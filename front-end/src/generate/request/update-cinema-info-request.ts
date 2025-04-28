/**
 * 修改影院信息请求体
 * @author <a href="mengnalidou.icu">mengnali_dou</a>
 */
export interface UpdateCinemaInfoRequest {
	// id
	cinemaId: number;

	// 影院名
	cinemaName: string;

	// 地址
	cinemaAddress: string;

	// 影院简介
	cinemaProfile?: string;

	// 影院服务
	cinemaService?: string;

	// 影院电话
	cinemaPhone?: string;

	// 影院交通
	cinemaTraffic?: string;
}
