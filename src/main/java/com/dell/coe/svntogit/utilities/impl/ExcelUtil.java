/**
 * 
 */
package com.dell.coe.svntogit.utilities.impl;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.poi.ss.usermodel.BorderStyle;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.FillPatternType;
import org.apache.poi.ss.usermodel.IndexedColors;
import org.apache.poi.xssf.usermodel.XSSFCellStyle;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import com.dell.coe.svntogit.framework.SvnToGitConstants;
import com.dell.coe.svntogit.model.CodeDir;
import com.dell.coe.svntogit.model.MigUserInfoModel;
import com.dell.coe.svntogit.model.Project;
import com.dell.coe.svntogit.utilities.IExcelUtil;

/**
 * @author Prashant_Gupta2
 *
 */
public class ExcelUtil implements IExcelUtil {
	
	XSSFCellStyle blueCellStyle;
	XSSFCellStyle greenCellStyle;
	XSSFCellStyle redCellStyle;
	XSSFWorkbook workbook;
	Project svnProject;
	Project gitProject;
	MigUserInfoModel instance;
	XSSFRow row;
	Cell cell;
	
	public ExcelUtil(Project svnProject,Project gitProject, MigUserInfoModel modelInstance){
		this.svnProject = svnProject;
		this.gitProject = gitProject;
		this.instance = modelInstance;
		workbook = new XSSFWorkbook();
		blueCellStyle = workbook.createCellStyle();
		blueCellStyle.setFillBackgroundColor(IndexedColors.TURQUOISE.getIndex());
		blueCellStyle.setFillForegroundColor(IndexedColors.TURQUOISE.getIndex());
		blueCellStyle.setFillPattern(FillPatternType.SOLID_FOREGROUND);
		blueCellStyle.setBorderBottom(BorderStyle.MEDIUM);
		blueCellStyle.setBorderTop(BorderStyle.MEDIUM);
		blueCellStyle.setBorderLeft(BorderStyle.MEDIUM);
		blueCellStyle.setBorderRight(BorderStyle.MEDIUM);
		
		
		greenCellStyle = workbook.createCellStyle();
		greenCellStyle.setFillBackgroundColor(IndexedColors.LIGHT_GREEN.getIndex());
		greenCellStyle.setFillForegroundColor(IndexedColors.LIGHT_GREEN.getIndex());
		greenCellStyle.setFillPattern(FillPatternType.SOLID_FOREGROUND);
		greenCellStyle.setBorderBottom(BorderStyle.NONE);
		greenCellStyle.setBorderTop(BorderStyle.NONE);
		greenCellStyle.setBorderLeft(BorderStyle.NONE);
		greenCellStyle.setBorderRight(BorderStyle.NONE);
		
		redCellStyle = workbook.createCellStyle();
		redCellStyle.setFillBackgroundColor(IndexedColors.ROSE.getIndex());
		redCellStyle.setFillForegroundColor(IndexedColors.ROSE.getIndex());
		redCellStyle.setFillPattern(FillPatternType.SOLID_FOREGROUND);
		redCellStyle.setBorderBottom(BorderStyle.NONE);
		redCellStyle.setBorderTop(BorderStyle.NONE);
		redCellStyle.setBorderLeft(BorderStyle.NONE);
		redCellStyle.setBorderRight(BorderStyle.NONE);
		
	}
	
	/* (non-Javadoc)
	 * @see com.dell.coe.svntogit.utilities.impl.IExcel#writeProjectDetails(org.apache.poi.xssf.usermodel.XSSFSheet, int, org.apache.poi.ss.usermodel.Cell, org.apache.poi.xssf.usermodel.XSSFRow)
	 */
	@Override
	public int writeProjectDetails(XSSFSheet spreadsheet, int rowCount, Cell cell, XSSFRow row){
		
		row = spreadsheet.createRow(rowCount++);
		cell = row.createCell(0);
		cell.setCellValue("Project Name:");
		cell.setCellStyle(blueCellStyle);
		cell = row.createCell(1);
		cell.setCellValue(instance.getProjectName());
		
		row = spreadsheet.createRow(rowCount++);
		
		row = spreadsheet.createRow(rowCount++);
		cell = row.createCell(0);
		cell.setCellValue("SVN Url");
		cell.setCellStyle(blueCellStyle);
		cell = row.createCell(1);
		cell.setCellValue(svnProject.getRepo_url());
		
		row = spreadsheet.createRow(rowCount++);
		cell = row.createCell(0);
		cell.setCellValue("GIT Url");
		cell.setCellStyle(blueCellStyle);
		cell = row.createCell(1);
		cell.setCellValue(gitProject.getRepo_url());
		
		row = spreadsheet.createRow(rowCount++);
		
		row = spreadsheet.createRow(rowCount++);
		cell = row.createCell(0);
		cell.setCellValue("No of Branches in SVN");
		cell.setCellStyle(blueCellStyle);
		cell = row.createCell(1);
		cell.setCellValue(svnProject.getBranches().size());
		
		row = spreadsheet.createRow(rowCount++);
		cell = row.createCell(0);
		cell.setCellValue("No of Branches in GIT");
		cell.setCellStyle(blueCellStyle);
		cell = row.createCell(1);
		cell.setCellValue(gitProject.getBranches().size());
		
		row = spreadsheet.createRow(rowCount++);
		
		cell = row.createCell(0);
		cell.setCellValue("No of Tags in SVN");
		cell.setCellStyle(blueCellStyle);
		cell = row.createCell(1);
		cell.setCellValue(svnProject.getTags().size());
	
		row = spreadsheet.createRow(rowCount++);
		cell = row.createCell(0);
		cell.setCellValue("No of Tags in GIT");
		cell.setCellStyle(blueCellStyle);
		cell = row.createCell(1);
		cell.setCellValue(gitProject.getTags().size());
		
		return rowCount;
		
	}
	
	/* (non-Javadoc)
	 * @see com.dell.coe.svntogit.utilities.impl.IExcel#writeBranches(org.apache.poi.xssf.usermodel.XSSFSheet, int, org.apache.poi.ss.usermodel.Cell, org.apache.poi.xssf.usermodel.XSSFRow)
	 */
	@Override
	public int writeBranches(XSSFSheet spreadsheet, int rowCount, Cell cell, XSSFRow row){
		
		try {
			List<CodeDir> svnBranches = svnProject.getBranches();
			List<CodeDir> dupSvnBranches = new ArrayList<CodeDir>();
			dupSvnBranches.addAll(svnBranches);
			List<CodeDir> gitBranches = gitProject.getBranches();
			List<CodeDir> dupGitBranches = new ArrayList<CodeDir>();
			dupGitBranches.addAll(gitBranches);
			int sizeSvnBranches = svnBranches.size();
			int sizeGitBranches = gitBranches.size();
			
			if( sizeSvnBranches >= sizeGitBranches){
				Map<String,CodeDir> gitBranchMap = new HashMap<String, CodeDir>();
				for(CodeDir gitBranch:gitBranches){
					gitBranchMap.put(gitBranch.getName(),gitBranch);
				}
				for(CodeDir svnBranch:svnBranches){
						row = spreadsheet.createRow(rowCount++);
						CodeDir gitBranchCompSvnBranch = gitBranchMap.get(svnBranch.getName());
						if(gitBranchCompSvnBranch!=null){
							
								dupGitBranches.remove(gitBranchCompSvnBranch);
								dupSvnBranches.remove(svnBranch);
								//row = spreadsheet.createRow(rowCount++);
								
								cell = row.createCell(0);
								cell.setCellValue(svnBranch.getName());
								cell = row.createCell(3);
								cell.setCellValue(gitBranchCompSvnBranch.getName());
								
								cell = row.createCell(1);
								cell.setCellValue(svnBranch.getUrl());
								cell = row.createCell(4);
								cell.setCellValue(gitBranchCompSvnBranch.getUrl());
								
								cell = row.createCell(2);
								cell.setCellValue(svnBranch.getSvnfiles().size());
								cell = row.createCell(5);
								cell.setCellValue(gitBranchCompSvnBranch.getSvnfiles().size());
							
								if(gitBranchCompSvnBranch.getSvnfiles().size()==svnBranch.getSvnfiles().size()){
									cell = row.createCell(6);
									cell.setCellValue("YES");
									cell.setCellStyle(greenCellStyle);
								}
							}	
				}
						int dupRowCount = rowCount;
						
						for(CodeDir gitBranch:dupGitBranches){
							row = spreadsheet.createRow(rowCount++);
							
							cell = row.createCell(3);
							cell.setCellValue(gitBranch.getName());
							
							cell = row.createCell(4);
							cell.setCellValue(gitBranch.getUrl());
							
							cell = row.createCell(5);
							cell.setCellValue(gitBranch.getSvnfiles().size());

							cell = row.createCell(6);
							cell.setCellValue("NO");
							cell.setCellStyle(redCellStyle);
							
						}
						
						for(CodeDir svnBranch1:dupSvnBranches){
							row = spreadsheet.createRow(dupRowCount++);
							
							cell = row.createCell(0);
							cell.setCellValue(svnBranch1.getName());
							
							cell = row.createCell(1);
							cell.setCellValue(svnBranch1.getUrl());
							
							cell = row.createCell(2);
							cell.setCellValue(svnBranch1.getSvnfiles().size());
							
							cell = row.createCell(6);
							cell.setCellValue("NO");
							cell.setCellStyle(redCellStyle);
						
						}
				
				
			}else{
				Map<String,CodeDir> svnBranchMap = new HashMap<String, CodeDir>();
				for(CodeDir svnBranch:svnBranches){
					svnBranchMap.put(svnBranch.getName(),svnBranch);
				}
				for(CodeDir gitBranch:gitBranches){
					row = spreadsheet.createRow(rowCount++);
					CodeDir svnBranchCompSvnBranch = svnBranchMap.get(gitBranch.getName());
					if(svnBranchCompSvnBranch!=null){
						
							dupSvnBranches.remove(svnBranchCompSvnBranch);
							dupGitBranches.remove(gitBranch);
							row = spreadsheet.createRow(rowCount++);
							
							cell = row.createCell(3);
							cell.setCellValue(gitBranch.getName());
							cell = row.createCell(0);
							cell.setCellValue(svnBranchCompSvnBranch.getName());
							
							cell = row.createCell(4);
							cell.setCellValue(gitBranch.getUrl());
							cell = row.createCell(1);
							cell.setCellValue(svnBranchCompSvnBranch.getUrl());
							
							cell = row.createCell(5);
							cell.setCellValue(gitBranch.getSvnfiles().size());
							cell = row.createCell(2);
							cell.setCellValue(svnBranchCompSvnBranch.getSvnfiles().size());
						
							if(svnBranchCompSvnBranch.getSvnfiles().size()==gitBranch.getSvnfiles().size()){
								cell = row.createCell(6);
								cell.setCellValue("YES");
								cell.setCellStyle(greenCellStyle);
							}
					}	
				}
				
				int dupRowCount = rowCount;
				
				for(CodeDir gitBranch:dupGitBranches){
					row = spreadsheet.createRow(rowCount++);
					
					cell = row.createCell(3);
					cell.setCellValue(gitBranch.getName());
					
					cell = row.createCell(4);
					cell.setCellValue(gitBranch.getUrl());
					
					cell = row.createCell(5);
					cell.setCellValue(gitBranch.getSvnfiles().size());
					
					cell = row.createCell(6);
					cell.setCellValue("NO");
					cell.setCellStyle(redCellStyle);
				}
				
				for(CodeDir svnBranch:dupSvnBranches){
					row = spreadsheet.createRow(dupRowCount++);
					
					cell = row.createCell(0);
					cell.setCellValue(svnBranch.getName());
					
					cell = row.createCell(1);
					cell.setCellValue(svnBranch.getUrl());
					
					cell = row.createCell(2);
					cell.setCellValue(svnBranch.getSvnfiles().size());
					
					cell = row.createCell(6);
					cell.setCellValue("NO");
					cell.setCellStyle(redCellStyle);
				
				}
				
			}
			
			return rowCount;
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return 0;
		}
		
	}

	/* (non-Javadoc)
	 * @see com.dell.coe.svntogit.utilities.impl.IExcel#writeToExcel()
	 */
	@Override
	public boolean writeToExcel() throws IOException {
		File file = new File(instance.getHomeLocation()+"//SummarizedSheet.xlsx");
		file.createNewFile();
		try (FileOutputStream out = new FileOutputStream(file);) {
			
			int rowCount = 0;
			XSSFSheet spreadsheet = workbook.createSheet("Summary");
			row = spreadsheet.createRow(0);
			cell = row.createCell(0);
			
			rowCount = writeProjectDetails(spreadsheet, rowCount, cell, row);
			
			row = spreadsheet.createRow(rowCount++);
			
			cell = row.createCell(0);
			cell.setCellValue("Branch in SVN");
			cell.setCellStyle(blueCellStyle);
			cell = row.createCell(1);
			cell.setCellValue("Url");
			cell.setCellStyle(blueCellStyle);
			cell = row.createCell(2);
			cell.setCellValue("No of Files");
			cell.setCellStyle(blueCellStyle);
			cell = row.createCell(3);
			cell.setCellValue("Branches in GIT");
			cell.setCellStyle(blueCellStyle);
			cell = row.createCell(4);
			cell.setCellValue("Url");
			cell.setCellStyle(blueCellStyle);
			cell = row.createCell(5);
			cell.setCellValue("No of Files");
			cell.setCellStyle(blueCellStyle);
			cell = row.createCell(6);
			cell.setCellValue("MATCH");
			cell.setCellStyle(blueCellStyle);
			
			rowCount = writeBranches(spreadsheet,rowCount,cell,row);
			
			row = spreadsheet.createRow(rowCount++);
			cell = row.createCell(0);
			cell.setCellStyle(blueCellStyle);
			cell.setCellValue("Tag in SVN");
			cell = row.createCell(1);
			cell.setCellStyle(blueCellStyle);
			cell.setCellValue("Url");
			cell = row.createCell(2);
			cell.setCellStyle(blueCellStyle);
			cell.setCellValue("No of Files");
			cell = row.createCell(3);
			cell.setCellStyle(blueCellStyle);
			cell.setCellValue("Tag in GIT");
			cell = row.createCell(4);
			cell.setCellStyle(blueCellStyle);
			cell.setCellValue("Url");
			cell = row.createCell(5);
			cell.setCellStyle(blueCellStyle);
			cell.setCellValue("No of Files");
			cell = row.createCell(6);
			cell.setCellStyle(blueCellStyle);
			cell.setCellValue("MATCH");
			
			rowCount = writeTags(spreadsheet,rowCount,cell,row);
			
			row = spreadsheet.createRow(rowCount++);
			cell = row.createCell(0);
			cell.setCellValue("Files in Trunk in SVN");
			cell.setCellStyle(blueCellStyle);
			
			cell = row.createCell(1);
			cell.setCellValue(svnProject.getTrunk().getSvnfiles().size());
			cell.setCellStyle(blueCellStyle);

			row = spreadsheet.createRow(rowCount++);
			cell = row.createCell(0);
			cell.setCellValue("Files in Master in GIT");
			cell.setCellStyle(blueCellStyle);
			
			cell = row.createCell(1);
			cell.setCellValue(gitProject.getTrunk().getSvnfiles().size());
			cell.setCellStyle(blueCellStyle);
			
			spreadsheet.autoSizeColumn(0);
			spreadsheet.autoSizeColumn(1);
			spreadsheet.autoSizeColumn(2);
			spreadsheet.autoSizeColumn(3);
			spreadsheet.autoSizeColumn(4);
			spreadsheet.autoSizeColumn(5);
			spreadsheet.autoSizeColumn(6);
			
			workbook.write(out);

		}
		boolean result = (svnProject.getBranches().size() == gitProject.getBranches().size()) && (svnProject.getTags().size()==gitProject.getTags().size()) && (svnProject.getTrunk().getSvnfiles().size() == gitProject.getTrunk().getSvnfiles().size());
		return result;
	}

	/* (non-Javadoc)
	 * @see com.dell.coe.svntogit.utilities.impl.IExcel#writeTags(org.apache.poi.xssf.usermodel.XSSFSheet, int, org.apache.poi.ss.usermodel.Cell, org.apache.poi.xssf.usermodel.XSSFRow)
	 */
	@Override
	public int writeTags(XSSFSheet spreadsheet, int rowCount, Cell cell,
			XSSFRow row) {

		List<CodeDir> svnTags = svnProject.getTags();
		List<CodeDir> dupSvnTags = new ArrayList<CodeDir>();
		dupSvnTags.addAll(svnTags);
		List<CodeDir> gitTags = gitProject.getTags();
		List<CodeDir> dupGitTags = new ArrayList<CodeDir>();
		dupGitTags.addAll(gitTags);
		int sizeSvnTags = svnTags.size();
		int sizeGitTags = gitTags.size();
		if(sizeSvnTags >= sizeGitTags){
			Map<String,CodeDir> gitTagMap = new HashMap<String, CodeDir>();
			for(CodeDir gitTag:gitTags){
				gitTagMap.put(gitTag.getName(),gitTag);
			}
			for(CodeDir svnTag:svnTags){
				row = spreadsheet.createRow(rowCount++);
				CodeDir gitTagCompSvnTag = gitTagMap.get(svnTag.getName());
				if(gitTagCompSvnTag!=null){
					
						dupGitTags.remove(gitTagCompSvnTag);
						dupSvnTags.remove(svnTag);
						//row = spreadsheet.createRow(rowCount++);
						
						cell = row.createCell(0);
						cell.setCellValue(svnTag.getName());
						cell = row.createCell(3);
						cell.setCellValue(gitTagCompSvnTag.getName());
						
						cell = row.createCell(1);
						cell.setCellValue(svnTag.getUrl());
						cell = row.createCell(4);
						cell.setCellValue(gitTagCompSvnTag.getUrl());
						
						cell = row.createCell(2);
						cell.setCellValue(svnTag.getSvnfiles().size());
						cell = row.createCell(5);
						cell.setCellValue(gitTagCompSvnTag.getSvnfiles().size());
					
						if(gitTagCompSvnTag.getSvnfiles().size()==svnTag.getSvnfiles().size()){
							cell = row.createCell(6);
							cell.setCellValue("YES");
							cell.setCellStyle(greenCellStyle);
						}
					}	
			}
				
				int dupRowCount = rowCount;
				
				for(CodeDir gitTag:dupGitTags){
					row = spreadsheet.createRow(rowCount++);
					
					cell = row.createCell(3);
					cell.setCellValue(gitTag.getName());
					
					cell = row.createCell(4);
					cell.setCellValue(gitTag.getUrl());
					
					cell = row.createCell(5);
					cell.setCellValue(gitTag.getSvnfiles().size());

					cell = row.createCell(6);
					cell.setCellValue("NO");
					cell.setCellStyle(redCellStyle);
					
				}
				
				for(CodeDir svnTag1:dupSvnTags){
					row = spreadsheet.createRow(dupRowCount++);
					
					cell = row.createCell(0);
					cell.setCellValue(svnTag1.getName());
					
					cell = row.createCell(1);
					cell.setCellValue(svnTag1.getUrl());
					
					cell = row.createCell(2);
					cell.setCellValue(svnTag1.getSvnfiles().size());
					
					cell = row.createCell(6);
					cell.setCellValue("NO");
					cell.setCellStyle(redCellStyle);
				
				}
			
		}else{
			Map<String,CodeDir> svnTagMap = new HashMap<String, CodeDir>();
			for(CodeDir svnTag:svnTags){
				svnTagMap.put(svnTag.getName(),svnTag);
			}
			for(CodeDir gitTag:gitTags){
				row = spreadsheet.createRow(rowCount++);
				CodeDir svnTagCompGitTag = svnTagMap.get(gitTag.getName());
				if(svnTagCompGitTag!=null){
					
						dupSvnTags.remove(svnTagCompGitTag);
						dupGitTags.remove(gitTag);
						row = spreadsheet.createRow(rowCount++);
						
						cell = row.createCell(3);
						cell.setCellValue(gitTag.getName());
						cell = row.createCell(0);
						cell.setCellValue(svnTagCompGitTag.getName());
						
						cell = row.createCell(4);
						cell.setCellValue(gitTag.getUrl());
						cell = row.createCell(1);
						cell.setCellValue(svnTagCompGitTag.getUrl());
						
						cell = row.createCell(5);
						cell.setCellValue(gitTag.getSvnfiles().size());
						cell = row.createCell(2);
						cell.setCellValue(svnTagCompGitTag.getSvnfiles().size());
					
						if(svnTagCompGitTag.getSvnfiles().size()==gitTag.getSvnfiles().size()){
							cell = row.createCell(6);
							cell.setCellValue("YES");
							cell.setCellStyle(greenCellStyle);
						}
				}	
			}
			
			int dupRowCount = rowCount;
			
			for(CodeDir gitBranch:dupGitTags){
				row = spreadsheet.createRow(rowCount++);
				
				cell = row.createCell(3);
				cell.setCellValue(gitBranch.getName());
				
				cell = row.createCell(4);
				cell.setCellValue(gitBranch.getUrl());
				
				cell = row.createCell(5);
				cell.setCellValue(gitBranch.getSvnfiles().size());
				
				cell = row.createCell(6);
				cell.setCellValue("NO");
				cell.setCellStyle(redCellStyle);
			}
			
			for(CodeDir svnBranch:dupSvnTags){
				row = spreadsheet.createRow(dupRowCount++);
				
				cell = row.createCell(0);
				cell.setCellValue(svnBranch.getName());
				
				cell = row.createCell(1);
				cell.setCellValue(svnBranch.getUrl());
				
				cell = row.createCell(2);
				cell.setCellValue(svnBranch.getSvnfiles().size());
				
				cell = row.createCell(6);
				cell.setCellValue("NO");
				cell.setCellStyle(redCellStyle);
			
			}
			
		}
		
		return rowCount;
		
	
	}

}
