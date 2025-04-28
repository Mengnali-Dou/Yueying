import requestApi from "@/generate/api-client/request.api.ts";
import { AddCinemaRequest, UpdateCinemaInfoRequest } from "@/generate/request/requests.ts";

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
 * 修改影院信息
 * @param updateCinemaInfoRequest 修改影院信息请求体
 */
export const updateCinemaInfoApi = (updateCinemaInfoRequest: UpdateCinemaInfoRequest) => {
	return requestApi({
		url: "/cinema/update",
		method: "PUT",
		headers: { "Content-Type": "application/json" },
		data: updateCinemaInfoRequest,
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
