<script lang="ts">
	import { onMount } from "svelte";
	import LoadingRecommendations from "./lib/LoadingRecommendations.svelte";
	import LoadingRestaurants from "./lib/LoadingRestaurants.svelte";
	import Recommendation from "./lib/Recommendation.svelte";
	import SearchBar from "./lib/SearchBar.svelte";

	let load: LoadingRestaurants;
	let search: SearchBar;
	let loadingVisible = false;
	let recommendations: Restaurant[] = [];

	let restaurantList: string[][] | null = [
		[],
		["haskell", "C plus plus", "go", "nim", "vim", "rust"],
	];
	onMount(async () => {
		load.hide();
		const restaurantReq = await fetch("http://localhost:8080/get_names");
		restaurantList = await restaurantReq.json();
	});

	const getRecommendation = async (name: string) => {
		console.log("Sending request for " + name);
		search.disable();
		loadingVisible = true;
		// Request Recommendations
		const recommendationReq = await fetch(
			"http://localhost:8080/recommend?name=" + search.getInput()
		);
		recommendations = await recommendationReq.json();
		console.log(recommendations);
		loadingVisible = false;
		search.enable();
	};
</script>

<main>
	<LoadingRestaurants bind:this={load} />
	<SearchBar bind:this={search} {getRecommendation} list={restaurantList[1]} />
	<LoadingRecommendations visible={loadingVisible} />
	<div class="recommendations">
		{#each recommendations as rec}
			{#if rec != null}
				<Recommendation text={rec} />
			{/if}
		{/each}
	</div>
</main>

<style>
	main {
		width: 100%;
		padding-top: 30px;
		max-width: 1000px;
		margin: auto;
		min-height: calc(100vh - 30px);
		background-color: #0006;
	}
	.recommendations {
		display: flex;
		justify-content: center;
		flex-wrap: wrap;
		width: 90%;
		margin: auto;
		margin-top: 20px;
	}
</style>
