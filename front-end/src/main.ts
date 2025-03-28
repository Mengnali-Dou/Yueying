import axios from "axios";
import router from "@/router";
import App from "./App.vue";
import { createApp } from "vue";
import { createPinia } from "pinia";
import { setupI18n } from "@/plugins/i18n";
import piniaPluginPersistedstate from "pinia-plugin-persistedstate";

import "@/style/login.scss";

const pinia = createPinia();
const app = createApp(App);

pinia.use(piniaPluginPersistedstate);

axios.defaults.baseURL = "/api";

setupI18n(app);

app.use(pinia);
app.use(router);
app.mount("#app");
