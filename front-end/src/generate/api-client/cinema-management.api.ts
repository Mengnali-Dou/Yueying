import requestApi from "@/generate/api-client/request.api.ts";

/**
 * 搜索影院
 * @param cinemaName 影院名
 */
export const searchCinemaInfoApi = (cinemaName?: string) => {
	return requestApi({
		url: `/cinema/search?cinemaName=${cinemaName ?? ""}`,
		method: "GET",
		headers: { "Content-Type": "application/json" },
	});
};
