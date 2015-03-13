package com.yingnet;

import java.io.File;
import java.io.FileFilter;
import java.io.IOException;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.io.filefilter.WildcardFileFilter;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.datasource.DriverManagerDataSource;
import org.springframework.util.StringUtils;

public class CompanyFilter {

	// 城市
	private final static String cityCode = "2012";

	private static JdbcTemplate jdbc;

	static {
		DriverManagerDataSource ds = new DriverManagerDataSource();
		ds.setUrl("jdbc:mysql://103.254.76.2:3307/elib?autoReconnect=true&socketTimeout=300000&connectionTimeout=300000&maxReconnects=3");
		ds.setUsername("elib");
		ds.setPassword("PHCkJ88F4");
		ds.setDriverClassName("com.mysql.jdbc.Driver");

		jdbc = new JdbcTemplate(ds);
	}

	public static void main(String[] args) throws IOException {

		File dir = new File("D://elib//" + cityCode + "//");
		FileFilter fileFilter = new WildcardFileFilter("*.xls");
		File[] files = dir.listFiles(fileFilter);
		// 循环文件
		for (File f : files) {
			List<String[]> companyList = JExcelUtils.readLines(f);

			// 去重
			List<String[]> companyListResult = remove(companyList);

			System.out.println("文件：" + f.getName() + "  不重名公司有："
					+ companyListResult.size());

			System.out.println("问题公司：");
			for (String[] companyArr : companyListResult) {
				String companyName = companyArr[0];
				String tel = companyArr[1];

				if (StringUtils.isEmpty(companyName)
						&& StringUtils.isEmpty(tel)) {
					continue;
				}

				String sql = "select id,name, teltag,emailtag from company where city = '"
						+ cityCode + "' and name = '" + companyName + "'";

				List<Dto> dtoList = jdbc.query(sql, new RowMapper<Dto>() {

					@Override
					public Dto mapRow(ResultSet rs, int index)
							throws SQLException {
						Dto dto = new Dto();
						dto.setId(rs.getString("id"));
						dto.setName(rs.getString("name"));
						dto.setTeltag(rs.getString("teltag"));
						dto.setEmailtag(rs.getString("emailtag"));

						return dto;
					}
				});

				if (dtoList == null || dtoList.size() == 0) {
					System.out.println(companyName);
					continue;
				}

				String cid = dtoList.get(0).getId();

				if (StringUtils.isEmpty(tel)) {
					jdbc.update("delete from company_link where cid in (" + cid
							+ ")");
					jdbc.update("update company set teltag = '0' where id in ("
							+ cid + ")");
				} else {
					jdbc.update("delete from company_link where cid in (" + cid
							+ ")  and tel = '" + tel + "'");
					jdbc.update("update company set teltag = '0' where id in ("
							+ cid + ")");
				}
			}

			System.out.println();
			System.out.println();
			System.out.println();
			System.out.println();

		}

	}

	private static List<String[]> remove(List<String[]> companyList) {

		List<String[]> companyListResult = new ArrayList<String[]>();
		for (int i = 0; i < companyList.size(); i++) {

			String[] sourceArr = companyList.get(i);
			boolean addTag = true;
			for (String[] resultArr : companyListResult) {
				
				if(StringUtils.isEmpty(resultArr[0])){
					continue;
				}
				
				if (sourceArr[0].equals(resultArr[0])) {
					System.out.println("重复的公司：" + resultArr[0]);
					addTag = false;
					break;
				}
			}
			if (addTag) {
				companyListResult.add(sourceArr);
			}
		}
		return companyListResult;
	}

}
