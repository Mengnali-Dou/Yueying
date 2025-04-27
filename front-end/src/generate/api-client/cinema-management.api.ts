import requestApi from "@/generate/api-client/request.api.ts";
import { AddCinemaRequest } from "@/generate/request/requests.ts";

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

/**
 * 添加影院
 * @param addCinemaRequest 添加影院请求体
 */
export const addCinemaApi = (addCinemaRequest: AddCinemaRequest) => {
	return requestApi({
		url: "/cinema/add",
		method: "POST",
		headers: { "Content-Type": "application/json" },
		data: addCinemaRequest,
	});
};

/**
 * 删除影院
 * @param cinemaId 影院id
 */
export const deleteCinemaApi = (cinemaId: number) => {
	return requestApi({
		url: `/cinema/delete?cinemaId=${cinemaId}`,
		method: "DELETE",
		headers: { "Content-Type": "application/json" },
	});
};
