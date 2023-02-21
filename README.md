# Yelp Recommender

This is an application that attempts to replicate yelp's recommendation algorithm.

# Dataset setup

0. Make sure `npm` is installed

1. Download the [dataset](https://yelp.com/dataset)

2. Use the datanarrower to trim the data to the first 10000 businesses, and all related reviews:

Put the `yelp_academic_dataset_business.json` and `yelp_academic_dataset_review.json` in the `data` folder in the root directory.

```bash
cd datanarrower
npm install
npx ts-node narrow.ts
```

This should generate a file named `dataset.json`.
At this point if you would like to throw away the original files, you may.

# Devserver setup

1. Install [maven](https://www.baeldung.com/install-maven-on-windows-linux-mac)

2. Run the executable to run a developer instance of the application

```bash
./mvnw spring-boot:run
```

If you would like to compile the application into a .jar file:

```bash
./mvnw clean package
```

# Svelte Webserver Setup

0. Make sure `npm` is installed

1. cd into the directory and install the dependencies

```bash
cd svelte
npm install
```

2. Run the dev server:

```bash
npm run dev
```

3. If you would like to build the website into the static site:

```bash
npm run build
```

If you would like, you can also run `npm run preview` after `npm run dev` to preview the builded version of the site
to verify everything is working.
