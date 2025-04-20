import {
	AddMovieRequest,
	AddMovieTypeRequest,
	SearchMovieInfoRequest,
	SearchMovieTypeRequest,
	UpdateMovieInfoRequest,
} from "@/generate/request/requests.ts";
import requestApi from "@/generate/api-client/request.api.ts";
import { UpdateMovieTypeRequest } from "@/generate/request/update-movie-type-request.ts";

/**
 * 搜索影片信息
 * @param searchMovieInfoRequest 搜索影片信息请求体
 */
export const searchMovieInfoApi = (searchMovieInfoRequest: SearchMovieInfoRequest) => {
	return requestApi({
		url: `/movie/search?movieName=${searchMovieInfoRequest.movieName}&movieTypeId=${searchMovieInfoRequest.movieTypeId}`,
		method: "GET",
		headers: { "Content-Type": "application/json" },
	});
};

/**
 * 添加影片
 * @param addMovieRequest 添加影片请求体
 */
export const addMovieApi = (addMovieRequest: AddMovieRequest) => {
	return requestApi({
		url: "/movie/add",
		method: "POST",
		headers: { "Content-Type": "application/json" },
		data: addMovieRequest,
	});
};

/**
 * 修改影片信息
 * @param updateMovieInfoRequest 修改影片信息请求体
 */
export const updateMovieInfoApi = (updateMovieInfoRequest: UpdateMovieInfoRequest) => {
	return requestApi({
		url: "/movie/update",
		method: "PUT",
		headers: { "Content-Type": "application/json" },
		data: updateMovieInfoRequest,
	});
};

/**
 * 删除影片
 * @param movieId 影片id
 */
export const deleteMovieApi = (movieId: number) => {
	return requestApi({
		url: `/movie/delete?movieId=${movieId}`,
		method: "DELETE",
		headers: { "Content-Type": "application/json" },
	});
};

/**
 * 搜索影片类型
 * @param searchMovieTypeRequest 搜索影片类型请求体
 */
export const searchMovieTypeApi = (searchMovieTypeRequest: SearchMovieTypeRequest) => {
	return requestApi({
		url: `/movie/search-type?movieTypeId=${searchMovieTypeRequest.movieTypeId}`,
		method: "GET",
		headers: { "Content-Type": "application/json" },
	});
};

/**
 * 添加影片类型
 * @param addMovieTypeRequest 添加影片类型请求体
 */
export const addMovieTypeApi = (addMovieTypeRequest: AddMovieTypeRequest) => {
	return requestApi({
		url: "/movie/add-type",
		method: "POST",
		headers: { "Content-Type": "application/json" },
		data: addMovieTypeRequest,
	});
};

/**
 * 修改影片类型
 * @param updateMovieTypeRequest 修改影片类型请求体
 */
export const updateMovieTypeApi = (updateMovieTypeRequest: UpdateMovieTypeRequest) => {
	return requestApi({
		url: "/movie/update-type",
		method: "PUT",
		headers: { "Content-Type": "application/json" },
		data: updateMovieTypeRequest,
	});
};

/**
 * 删除影片类型
 * @param movieTypeId 影片类型ID
 */
export const deleteMovieTypeApi = (movieTypeId: number) => {
	return requestApi({
		url: `/movie/delete-type?movieTypeId=${movieTypeId}`,
		method: "DELETE",
		headers: { "Content-Type": "application/json" },
	});
};
