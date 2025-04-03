import { AddMovieRequest, SearchMovieInfoRequest, SearchMovieTypeRequest } from "@/generate/request/requests.ts";
import requestApi from "@/generate/api-client/request.api.ts";

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
