<script lang="ts">
  import Star from "./Star.svelte";

  export let rating: number = 5;
  console.log(rating);

  type starType = "fill" | "half" | "empty";

  let ratingNumber = rating;
  const starArray: starType[] = [];
  while (ratingNumber > 0) {
    if (ratingNumber >= 1) { 
      starArray.push("fill");
      ratingNumber -= 1;
      continue;
    }

    if (rating >= 0.5) {
      starArray.push("half")
      ratingNumber -= 0.5;
      continue
    } 
  }
  while (starArray.length < 5) {
    starArray.push('empty')
  }

</script>

{#each starArray as star}
  <Star type={star} />
{/each}

<style>
</style>
