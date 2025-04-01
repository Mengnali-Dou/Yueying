const MovieManagement = () => import("@/views/movie-management/MovieManagement.vue");
const EmptyLayout = () => import("@/components/EmptyLayout.vue");

export const movieManagementRoutes = [
	{
		path: "/movie-management",
		name: "",
		component: EmptyLayout,
		children: [
			{
				path: "",
				name: "movie-management",
				component: MovieManagement,
				meta: {
					keepalive: true,
					breadcrumbName: "影片信息",
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
