const MovieManagement = () => import("@/views/movie-management/MovieManagement.vue");
const MovieTypeManagement = () => import("@/views/movie-management/movie-type-management/MovieTypeManagement.vue");
const EmptyLayout = () => import("@/components/EmptyLayout.vue");

export const movieManagementRoutes = [
	{
		path: "",
		name: "",
		component: EmptyLayout,
		children: [
			{
				path: "/movie-management",
				name: "movie-management",
				component: MovieManagement,
				meta: {
					keepalive: true,
					breadcrumbName: "影片信息",
					permission: "MovieManagement",
				},
			},
			{
				path: "/movie-type-management",
				name: "movie-type-management",
				component: MovieTypeManagement,
				meta: {
					keepalive: true,
					breadcrumbName: "影片类型管理",
					permission: "MovieManagement",
				},
			},
		],
		meta: {
			keepalive: true,
			breadcrumbName: "影片管理",
			permission: "MovieManagement",
		},
	},
];
