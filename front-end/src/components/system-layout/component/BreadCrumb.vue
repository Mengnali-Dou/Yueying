<script lang="ts">
export default {
	name: "BreadCrumb",
};
</script>

<template>
	<a-breadcrumb style="border-bottom: 1px solid #f0f0f0; padding-bottom: 5px; margin-bottom: 10px">
		<a-breadcrumb-item v-for="item in $route.matched.slice(1, $route.matched.length)">
			<router-link :to="item.path">
				{{ item.meta.breadcrumbName }}
			</router-link>
		</a-breadcrumb-item>
	</a-breadcrumb>
</template>
