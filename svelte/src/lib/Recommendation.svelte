<script lang="ts">
  export let text: string;
</script>

<div class="recommendation">
  <div class="text">{text}</div>
</div>

<style>
  .recommendation {
    transition: 0.3s;
    width: 40%;
    height: 150px;
    margin: 10px;
    padding: 5px;
    background-color: #fff;
    border-radius: 10px;
  }

  .recommendation:hover {
    transform: translateY(-5px);
  }
</style>
