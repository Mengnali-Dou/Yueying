/**
 * 修改影片信息请求体
 * @author <a href="mengnalidou.icu">mengnali_dou</a>
 */
export interface UpdateMovieInfoRequest {
	// id
	movieId: number;

	// 影片名
	movieName: string;

	// 影片类型id
	movieTypeId: number;

	// 影片封面（大）
	movieCoverLarge?: string;

	// 影片封面（小）
	movieCoverSmall?: string;

	// 上映时间
	releaseDate: string;

	// 时长
	movieDuration: string;

	// 主要演员
	mainActor?: string;

	// 简介
	movieProfile?: string;
}
