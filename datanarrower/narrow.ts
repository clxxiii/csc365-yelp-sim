import { readFileSync, writeFileSync } from "fs";

const data = readFileSync("./yelp_academic_dataset_business.json")
	.toString()
	.split("\n");

const set = [];
let i = 0;
for (const line of data) {
	const json: any = JSON.parse(line);

	if (
		json.categories != null &&
		json.categories.split(", ").includes("Restaurants")
	) {
		set.push(json);
		i++;
	}

	if (i >= 10000) break;
}

writeFileSync("./data/dataset.json", JSON.stringify(set));
