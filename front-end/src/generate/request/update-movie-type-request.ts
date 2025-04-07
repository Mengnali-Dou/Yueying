/**
 * 修改影片类型请求体
 */
export interface UpdateMovieTypeRequest {
	// id
	movieTypeId: number;

	// 影片类型
	movieTypeName: string;
}
