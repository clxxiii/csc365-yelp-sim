<script lang="ts">
  import {Fzf} from "fzf";
  import Prediction from "./Prediction.svelte";

  export let list: string[];
  export let getRecommendation: (name: string) => any;

  let fzf: Fzf<string[]>;
  let entries: string[] = [];
  let color: string;

  let input: HTMLInputElement;

  export function getInput() {
    return input.value;
  }
  const keydown: (event?: KeyboardEvent) => void = (event) => {
    // Initialize fzf 
    if (fzf == undefined && list != undefined) {
      fzf = new Fzf(list)
    }

    let find = fzf.find(input.value)
    fzf.find(input.value);
    entries = find.map(x => x.item).slice(0,10);

    if (event?.key == "Enter" && entries.includes(input.value)) {
      getRecommendation(input.value);
      entries = [];
      return
    }

    if (
      input.value == "" ||
      entries.includes(input.value)
      ) {
      entries = [];
      color = "#000"
      return;
    }

    if (entries.length == 0) {
      color = "#f00";
    } else {
      color = "#000"
    }
  }


  export const disable = () => {
    color = "#aaa";
    input.disabled = true;
  }

  export const enable = () => {
    color = "#000";
    input.disabled = false;
  }

  const boxselected = (text) => {
    input.value = text.detail.text;
    keydown();
    input.focus();
  }
</script>

<label>
<svg xmlns="http://www.w3.org/2000/svg" fill="none" viewBox="0 0 24 24" stroke-width="1.5" stroke="currentColor" class="w-6 h-6">
  <path stroke-linecap="round" stroke-linejoin="round" d="M21 21l-5.197-5.197m0 0A7.5 7.5 0 105.196 5.196a7.5 7.5 0 0010.607 10.607z" />
</svg>

<input style="color: {color}" on:keyup={keydown} bind:this={input} type="text" placeholder="Recommend me a restaurant like...">
</label>
<div class="predictions">
  {#each entries as text }
    <Prediction on:selected={boxselected} {text} />
  {/each}
</div>

<style>
  label {
    display: grid;
    width: 90%;
    margin: auto;
    background-color: white;
    grid-template-columns: 50px calc(100% - 50px);
    place-items: center;
    padding: 4px;
    height: 50px;
    cursor: text
  }
  label svg {
    stroke: #ccc;
    /* stroke-width: 3px; */
    height: 40px;
  }
  input {
    width: 100%;
    height: 50px;
    border: 0;
    padding: 0;
    font-family: 'Quicksand';
    font-size: 30px;
    color: #000;
    outline: none;
  }

  input::placeholder {
    color: #ccc;
  }
  .predictions {
    margin: auto;
    width: calc(90% + 4px*2);
  }
</style>
