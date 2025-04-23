<script setup lang="ts">
const props = withDefaults(
	defineProps<{
		text: string;
		maxLength?: number;
	}>(),
	{
		maxLength: 0,
	},
);
</script>

<template>
	<span v-if="!props.text">--</span>
	<a-tooltip v-else-if="props.maxLength > 0 && props.text.length > props.maxLength">
		<template #title>
			{{ props.text }}
		</template>
		{{ props.text.slice(0, props.text.length - props.maxLength) + "..." }}
	</a-tooltip>
	<span v-else>
		{{ props.text }}
	</span>
</template>

<style scoped></style>
