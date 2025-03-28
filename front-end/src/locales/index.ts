import { createI18n } from "vue-i18n";
import zh_CN from "@/locales/lang/zh_CN";
import en_US from "@/locales/lang/en_US";

const i18n: ReturnType<typeof createI18n> = createI18n({
	legacy: false,
	locale: "zh_CN",
	fallbackLocale: "zh_CN",
	messages: {
		zh_CN: zh_CN,
		en_US: en_US,
	},
});

export default i18n;
