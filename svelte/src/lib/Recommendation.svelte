<script lang="ts">
    import Stars from "./Stars.svelte";

  export let text: string;

  const data = JSON.parse(text)
  console.log(data);
</script>

<div class="recommendation">
  <div class="name">{data.name}</div>
  <div class="location">{data.city}, {data.state}</div>
  <div class="tags">
    <b>Tags:</b> {data.categories}
  </div>
  <div class="stars"><Stars rating={data.stars} /></div>
</div>

<style>
  .recommendation {
    position: relative;
    transition: 0.3s;
    width: 40%;
    height: 150px;
    margin: 10px;
    padding: 5px;
    background-color: #5557;
    color: white;
    border-radius: 10px;
  }
  .stars {
    position: absolute;
    top: 5px;
    left: 5px;
  }
  .name {
    padding-top: 20px;
    font-size: 1.5em;
  }
  .location {
    position: absolute;
    top: 5px;
    right: 5px;
  }
  .tags {
    position: absolute;
    width: 100%;
    padding: 5px;
    bottom: 0;
    left: 0;
    overflow: hidden;
    white-space: nowrap;
    text-overflow: ellipsis;
  }

  .recommendation:hover {
    transform: translateY(-5px);
  }
</style>
