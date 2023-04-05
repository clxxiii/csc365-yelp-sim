<script lang="ts">
	export let text: Restaurant;
</script>

<div class="recommendation">
	<div class="name">{text.name}</div>
	<div class="location">{text.state}</div>
	<div class="tags">
		<b>Tags:</b>
		{text.categories}
	</div>
</div>

<style>
	.recommendation {
		position: relative;
		transition: 0.3s;
		width: 40%;
		height: 60px;
		margin: 10px;
		padding: 5px;
		background-color: #5557;
		color: white;
		border-radius: 10px;
	}
	.name {
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
