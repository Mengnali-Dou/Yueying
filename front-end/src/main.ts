import { createApp } from "vue";
import "./style.css";
import App from "./App.vue";
import { createPinia } from "pinia";
import piniaPluginPersistedstate from "pinia-plugin-persistedstate";
import { setupI18n } from "./plugins/i18n.ts";

const pinia = createPinia();
const app = createApp(App);

pinia.use(piniaPluginPersistedstate);

setupI18n(app);

app.use(pinia);
app.mount("#app");
