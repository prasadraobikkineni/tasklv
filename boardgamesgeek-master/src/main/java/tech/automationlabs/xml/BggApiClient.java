package tech.automationlabs.xml;

import static io.restassured.RestAssured.given;

import java.util.ArrayList;
import java.util.List;

import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

public class BggApiClient {

	public static final BggApiClient bggApiClient = new BggApiClient();

	private BggApiClient() {
	}

	public List<String> getLanguageDependenceTopOptions(String gameId) {
		List<String> topOptions = new ArrayList<>();
		String xml = given().when().get("https://boardgamegeek.com/xmlapi/boardgame/" + gameId).body().asString();

		XmlParser parser = new XmlParser(xml);

		String totalVotes = parser.getString("/boardgames/boardgame/poll[@name='language_dependence']/@totalvotes");
		if ("0".equals(totalVotes)) {
			return topOptions;
		}

		NodeList results = parser.getNodes("/boardgames/boardgame/poll[@name='language_dependence']/results/result");
		int maxNumVotes = 0;
		for (int i = 0; i < results.getLength(); i++) {
			Node result = results.item(i);
			int numVotes = parser.getIntAttribute(result, "numvotes");
			String optionName = parser.getAttribute(result, "value");
			if (numVotes > maxNumVotes) {
				maxNumVotes = numVotes;
				topOptions = new ArrayList<>();
				topOptions.add(optionName);
			} else if (numVotes == maxNumVotes) {
				topOptions.add(optionName);
			}
		}

		return topOptions;
	}
}
