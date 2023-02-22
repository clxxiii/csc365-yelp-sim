<script lang="ts">
  import { onMount } from "svelte";
    import LoadingRecommendations from "./lib/LoadingRecommendations.svelte";
  import LoadingRestaurants from "./lib/LoadingRestaurants.svelte";
    import Recommendation from "./lib/Recommendation.svelte";
  import SearchBar from "./lib/SearchBar.svelte";

  let load: LoadingRestaurants;
  let search: SearchBar;
  let loadingVisible = false;
  let recommendations: string[] = [];

  let restaurantList: string[] | null = ['dev'];
  onMount(async () => {
    load.hide();
    const restaurantReq = await fetch("http://localhost:8080/get_names");
    restaurantList = await restaurantReq.json();
  });


  const getRecommendation = async (name: string) => {
      console.log("Sending request for " + name);
      search.disable();
      loadingVisible = true;
      recommendations = [];
      await new Promise(resolve => setTimeout(resolve, 3000)); // Literally just a sleep timer
      // Request Recommendations
      recommendations = ["This is a recommendation", "so is this"];
      loadingVisible = false;
      search.enable();
  }
</script>

<main>
  <LoadingRestaurants bind:this={load}/>
  <SearchBar bind:this={search} {getRecommendation} list={restaurantList}/>
  <LoadingRecommendations visible={loadingVisible}/>
  <div class="recommendations">
    {#each recommendations as rec}
      <Recommendation text={rec} />
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
    backdrop-filter: blur(4px);
  }
  .recommendations {
    display: flex;
    justify-content: center;
    width: 90%;
    margin: auto;
    margin-top: 20px;
  }
</style>
