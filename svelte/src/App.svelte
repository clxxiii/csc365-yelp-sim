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
      // Request Recommendations
      const recommendationReq = await fetch("http://localhost:8080/recommend?id=St%20Honore%20Pastries");
      recommendations = [`{"business_id":"MTSW4McQd7CbVtyjqoe9mw","name":"St Honore Pastries","address":"935 Race St","city":"Philadelphia","state":"PA","postal_code":"19107","latitude":39.9555052,"longitude":-75.1555641,"stars":4.0,"review_count":80,"is_open":1,"attributes":{"RestaurantsDelivery":"False","OutdoorSeating":"False","BusinessAcceptsCreditCards":"False","BusinessParking":"{'garage': False, 'street': True, 'validated': False, 'lot': False, 'valet': False}","BikeParking":"True","RestaurantsPriceRange2":"1","RestaurantsTakeOut":"True","ByAppointmentOnly":"False","WiFi":"u'free'","Alcohol":"u'none'","Caters":"True"},"categories":"Restaurants, Food, Bubble Tea, Coffee & Tea, Bakeries","hours":{"Monday":"7:0-20:0","Tuesday":"7:0-20:0","Wednesday":"7:0-20:0","Thursday":"7:0-20:0","Friday":"7:0-21:0","Saturday":"7:0-21:0","Sunday":"7:0-21:0"}}`, `{"business_id":"WKMJwqnfZKsAae75RMP6jA","name":"Roast Coffeehouse and Wine Bar","address":"10359 104 Street NW","city":"Edmonton","state":"AB","postal_code":"T5J 1B9","latitude":53.5460453,"longitude":-113.4991693,"stars":4.5,"review_count":40,"is_open":0,"attributes":{"OutdoorSeating":"False","Caters":"True","RestaurantsDelivery":"False","RestaurantsGoodForGroups":"True","RestaurantsPriceRange2":"2","RestaurantsReservations":"False","BusinessParking":"{'garage': False, 'street': True, 'validated': False, 'lot': True, 'valet': False}","HasTV":"False","GoodForKids":"False","RestaurantsTakeOut":"True","WiFi":"u'free'","NoiseLevel":"u'average'","RestaurantsAttire":"u'casual'","Alcohol":"u'beer_and_wine'","Ambience":"{'romantic': False, 'intimate': False, 'classy': False, 'hipster': True, 'touristy': False, 'trendy': True, 'upscale': False, 'casual': False}"},"categories":"Coffee & Tea, Food, Cafes, Bars, Wine Bars, Restaurants, Nightlife","hours":{"Monday":"8:0-18:0","Tuesday":"8:0-18:0","Wednesday":"8:0-18:0","Thursday":"8:0-18:0","Friday":"8:0-18:0","Saturday":"8:0-18:0","Sunday":"10:0-17:0"}}`];
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
  }
  .recommendations {
    display: flex;
    justify-content: center;
    width: 90%;
    margin: auto;
    margin-top: 20px;
  }
</style>
