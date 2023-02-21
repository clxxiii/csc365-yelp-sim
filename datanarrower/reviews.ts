import { readFileSync, writeFileSync } from "fs";

const data = readFileSync("./yelp_academic_dataset_review.json")
	.toString()
	.split("\n");

const businesses = JSON.parse(
	readFileSync("./data/dataset.json").toString()
).map((x: any) => x.name);

const set = [];
let i = 0;
for (const line of data) {
	const json: any = JSON.parse(line);
	console.log(json);
	break;

	// if (
	//   json.categories.split(", ").includes("Restaurants")
	// ) {
	//   set.push(json);
	//   i++;
	// }
	//
	// if (i >= 10000) break;
}
