import { createRouter, createWebHistory } from "vue-router";

const Home = import("@/views/Home.vue");
const Login = import("@/views/Login.vue");

const routers = [
	{
		path: "/",
		name: "",
		component: Home,
	},
	{
		path: "/login",
		name: "login",
		component: Login,
		meta: {
			keepalive: true,
		},
	},
];

const router = createRouter({
	history: createWebHistory(),
	routes: routers,
});

router.beforeEach((to, __from, next) => {
	if (to.name !== "login" && to.name !== "register" && !sessionStorage.getItem("userId")) {
		next({
			name: "login",
			query: {
				redirect: to.fullPath,
			},
		});
	} else {
		next();
	}
});

export default router;
