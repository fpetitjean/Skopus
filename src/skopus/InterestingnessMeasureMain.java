package skopus;
/*******************************************************************************
 * Copyright (C) 2015 Tao Li
 * 
 * This file is part of Skopus.
 * 
 * Skopus is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, version 3 of the License.
 * 
 * Skopus is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 * 
 * You should have received a copy of the GNU General Public License
 * along with Skopus.  If not, see <http://www.gnu.org/licenses/>.
 ******************************************************************************/
import java.io.File;
import java.text.SimpleDateFormat;
import java.util.Date;



public class InterestingnessMeasureMain {
	private static String strMInputFileName = "";
	private static String strMOutputPathName = "";
	
	public static void main(String... args) throws Exception {
		if(!parseParameters(args)) {
			return;
		}

		File fileInputDataFile = new File(strMInputFileName);
        if (!fileInputDataFile.exists()) {
            System.out.println("Input file does not exist : " + strMInputFileName + "\n");
            return;
        }
        else if(fileInputDataFile.isDirectory()) {
        	System.out.println("Input file does not exist : " + strMInputFileName + "\n");
            return;
        }
		
		File fileOutputPath = new File(strMOutputPathName);
		if (fileOutputPath.exists()) {
			if (fileOutputPath.isFile()) {
				System.out.println("Output path is wrong : " + strMOutputPathName + "\n");
				return;
			}
		}
 		else {
			fileOutputPath.mkdir();
		}
		
		strMOutputPathName = fileOutputPath.getAbsolutePath() + "/" + fileInputDataFile.getName();
		strMOutputPathName += "-k" + GlobalData.nK;
		switch (GlobalData.nInterestingnessMeasure) {
		case 1:
			strMOutputPathName += "-Support";
			break;
		case 2:
			strMOutputPathName += "-Leverage";
			break;
		default:
			break;
		}
		
		if(GlobalData.nMaxResultPatternLength > 0){
			strMOutputPathName += "-l" + GlobalData.nMaxResultPatternLength;
		}
		
		GlobalData.Init();
		
		FileLoader fl = new FileLoader();
		fl.loadData(strMInputFileName);

		ItemsetFinder isF = new ItemsetFinder();

		SimpleDateFormat df = new SimpleDateFormat("yyyyMMddHHmmss");
		
		isF.strDebugFile = strMOutputPathName + "-m" + GlobalData.dSmoothCoefficient +"-"+df.format(new Date())+"-debug.txt";
		java.io.File fileFileName = new java.io.File(isF.strDebugFile);
		if (fileFileName.exists()) {
			fileFileName.delete();
		}
				
		long startTime = System.currentTimeMillis();
		isF.generateResultItemsets();
		long finishTime =  System.currentTimeMillis();
		
		isF.outputResult(strMOutputPathName + "-m" + GlobalData.dSmoothCoefficient +"-result.txt");

		
		GlobalOper.appendFileContent(strMOutputPathName + "-m" + GlobalData.dSmoothCoefficient +"-result.txt", 
				"\n\n\nExecution time= "+(finishTime-startTime)+" ms" + "\n\n");
		
		System.out.println("Execution time= "+(finishTime-startTime)+" ms");
		System.out.println("OK");

	}

	private static boolean parseParameters(String[] args) {
		String strUsageString = "Usage: %s <input file> <output path> [-i <x>] [-m <v>] [-k <k>] [-l <n>] [-d]\n\n";
		strUsageString += "-i <x> : the interestingness measure, 1: Support(defalut), 2: Leverage\n";
		strUsageString += "-m <v>: use smoothed values, the default value  is 0.5\n";
		strUsageString += "-k <k>: the K number of top K, the default value is 100\n";
		strUsageString += "-d: output the debug information\n";
		strUsageString += "-l <n> : the maximum sequential pattern length\n";
		
		
		for (int i = 0; i < args.length; i++) {
			if ('-' == args[i].charAt(0)) {
				switch (args[i].charAt(1)) {
				case 'i':
					if (args[i].length() == 2) {
						i++;
						int n = Integer.parseInt(args[i]);
						if ((n < 1) || (n > 2)) {
							System.out.println(strUsageString);
							return false;
						}
						GlobalData.nInterestingnessMeasure = n;
					} else {
						int n = Integer.parseInt(args[i].substring(2));
						if ((n < 1) || (n > 2)) {
							System.out.println(strUsageString);
							return false;
						}
						GlobalData.nInterestingnessMeasure = n;
					}
					break;
				case 'd':
					GlobalData.bDebugInformation = true;
					break;
				case 'l':
					if (args[i].length() == 2) {
						i++;
						int n = Integer.parseInt(args[i]);
						if (n < 1) {
							GlobalData.nMaxResultPatternLength = 0;
						}else{
							GlobalData.nMaxResultPatternLength = n;
						}
					} else {
						int n = Integer.parseInt(args[i].substring(2));
						if (n < 1) {
							GlobalData.nMaxResultPatternLength = 0;
						}else{
							GlobalData.nMaxResultPatternLength = n;
						}
					}
					break;
				case 'm':
					GlobalData.bSmoothedValue = true;
					if (args[i].length() == 2) {
						i++;
						try {
							double n = Double.parseDouble(args[i]);
							if (n <= 0.0) {
								System.out.println(strUsageString);
								return false;
							}
							GlobalData.dSmoothCoefficient = n;
						} catch (Exception e) {
							i--;
							GlobalData.dSmoothCoefficient = 0.5;
						}
					} else {
						double n = Double.parseDouble(args[i].substring(2));
						if (n <= 0.0) {
							System.out.println(strUsageString);
							return false;
						}
						GlobalData.dSmoothCoefficient = n;
					}
					break;
				case 'k':
					if (args[i].length() == 2) {
						i++;
						int n = Integer.parseInt(args[i]);
						if (n <= 0) {
							System.out.println(strUsageString);
							return false;
						}
						GlobalData.nK = n;
					} else {
						int n = Integer.parseInt(args[i].substring(2));
						if (n <= 0) {
							System.out.println(strUsageString);
							return false;
						}
						GlobalData.nK = n;
					}
					break;
				default:
					System.out.println(strUsageString);
					return false;
				} // switch (args[i].charAt(1))
			}// if('-' == args[i].charAt(i))
			else if (strMInputFileName.length() < 1){
				strMInputFileName = args[i];
			}//else if (strMInputFileName.length() < 1)
			else if(strMOutputPathName.length() < 1){
				strMOutputPathName = args[i];
			}
			else{
				System.out.println(strUsageString);
				return false;
			}
		} // for(int i = 0; i<args.length; i++)
		
		
		
		return true;
	}
	
	

}
