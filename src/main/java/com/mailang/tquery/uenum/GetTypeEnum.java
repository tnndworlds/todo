package com.mailang.tquery.uenum;

public enum GetTypeEnum
{
	SQL,
	DIRECT,
	JSON,
	CODE;
	
	public static GetTypeEnum getType(String type)
	{
		for (GetTypeEnum tmpEnum : GetTypeEnum.values())
		{
			if (tmpEnum.name().equals(type))
			{
				return tmpEnum;
			}
		}
		return GetTypeEnum.DIRECT;
	}
}
