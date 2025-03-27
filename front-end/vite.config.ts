import { defineConfig } from "vite";
import vue from "@vitejs/plugin-vue";
import path from "path";
import { AntDesignVueResolver } from "unplugin-vue-components/resolvers";
import Components from "unplugin-vue-components/vite";

// https://vite.dev/config/
export default defineConfig({
	plugins: [
		vue(),
		Components({
			resolvers: [
				AntDesignVueResolver({
					importStyle: false, // css in js
				}),
			],
		}),
	],
	css: {
		preprocessorOptions: {
			scss: {
				additionalData: `@use "@/style/_variables.scss";`,
			},
		},
	},
	resolve: {
		alias: {
			"@": path.resolve(__dirname, "./src"),
		},
	},
});
