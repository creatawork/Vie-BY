/**
 * 省市区精简数据 (仅作演示和基础功能使用，实际项目建议引入完整 JSON)
 */

export type PCAItem = {
	name: string
	code: string
	children: PCAItem[]
}

export const pcaData : PCAItem[] = [
	{
		name: '北京市',
		code: '110000',
		children: [
			{
				name: '北京市',
				code: '110100',
				children: [
					{ name: '东城区', code: '110101', children: [] },
					{ name: '西城区', code: '110102', children: [] },
					{ name: '朝阳区', code: '110105', children: [] },
					{ name: '丰台区', code: '110106', children: [] },
					{ name: '石景山区', code: '110107', children: [] },
					{ name: '海淀区', code: '110108', children: [] }
				]
			}
		]
	},
	{
		name: '广东省',
		code: '440000',
		children: [
			{
				name: '广州市',
				code: '440100',
				children: [
					{ name: '越秀区', code: '440104', children: [] },
					{ name: '荔湾区', code: '440103', children: [] },
					{ name: '天河区', code: '440106', children: [] },
					{ name: '白云区', code: '440111', children: [] }
				]
			},
			{
				name: '深圳市',
				code: '440300',
				children: [
					{ name: '罗湖区', code: '440303', children: [] },
					{ name: '福田区', code: '440304', children: [] },
					{ name: '南山区', code: '440305', children: [] },
					{ name: '宝安区', code: '440306', children: [] }
				]
			}
		]
	},
	{
		name: '上海市',
		code: '310000',
		children: [
			{
				name: '上海市',
				code: '310100',
				children: [
					{ name: '黄浦区', code: '310101', children: [] },
					{ name: '徐汇区', code: '310104', children: [] },
					{ name: '长宁区', code: '310105', children: [] },
					{ name: '静安区', code: '310106', children: [] }
				]
			}
		]
	}
]

