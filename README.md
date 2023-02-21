# Yelp Recommender

This is an application that attempts to replicate yelp's recommendation algorithm.

# Dev setup

1. Download the [dataset](https://yelp.com/dataset)

2. Use the datanarrower to trim the data to the first 10000 businesses, and all related reviews:

Put the `yelp_academic_dataset_business.json` and `yelp_academic_dataset_review.json` in the `data` folder in the root directory.

```bash
npm install
npx ts-node datanarrower/narrow.ts
npx ts-node datanarrower/reviews.ts
```

This should generate two files, `dataset.json`, and `dataset_reviews.json`.
At this point if you would like to throw away the original files, you may.

3. something with springboot we haven't gotten there yet
