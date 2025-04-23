import { createRouter, createWebHistory } from "vue-router";
import { userManagementRoutes } from "@/router/user-management/user-management-router.ts";
import { movieManagementRoutes } from "@/router/movie-management/movie-management-router.ts";
import { cinemaManagementRoutes } from "@/router/cinema-management/cinema-management-router.ts";

const Home = () => import("@/views/Home.vue");
const Login = () => import("@/views/Login.vue");

const SystemLayout = () => import("@/components/system-layout/SystemLayout.vue");

const routers = [
	{
		path: "/",
		name: "",
		component: SystemLayout,
		children: [
			{
				path: "",
				name: "home",
				component: Home,
				meta: {
					keepalive: true,
					breadcrumbName: "首页",
				},
			},
			...userManagementRoutes,
			...movieManagementRoutes,
			...cinemaManagementRoutes,
		],
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
