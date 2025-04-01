import { SearchMovieInfoRequest } from "@/generate/request/requests.ts";
import requestApi from "@/generate/api-client/request.api.ts";

/**
 * 搜索影片信息
 * @param searchMovieInfoRequest 搜索影片信息请求体
 */
export const searchMovieInfoApi = (searchMovieInfoRequest: SearchMovieInfoRequest) => {
	return requestApi({
		url: `/movie/search?movieName=${searchMovieInfoRequest.movieName}&movieType=${searchMovieInfoRequest.movieType}`,
		method: "GET",
		headers: { "Content-Type": "application/json" },
	});
};
