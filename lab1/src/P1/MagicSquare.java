package P1;

import java.io.*;
import java.util.*;

public class MagicSquare {
	
    public static boolean isLegalMagicSquare(String fileName)  {//将矩阵从文件中读取，并求和判断是否为幻方
    	
    	//从文件中读取矩阵，若读取失败将返回false
    	File file = new File(fileName);
    	List<String> lines = new ArrayList<String>();
    	BufferedReader reader = null;
    	try {
    		reader = new BufferedReader(new FileReader(file));
    		String myline;
    		while((myline = reader.readLine()) != null) {
    			lines.add(myline);
    		}
    		reader.close();
    	}catch (FileNotFoundException e) {
    		System.out.println("Cannot find the specified file.");
    		return false;
    	}catch(IOException e) {
    		System.out.println("File read error.");
    		return false;
    	}
    	
    	List<ArrayList<Integer>> matrix = new ArrayList<>();
    	int len = lines.size();
    	for(int i = 0; i < len; i++) {
    		ArrayList<Integer> lineofmatrix = new ArrayList<Integer>();
    		String myline = lines.get(i);
    		String[] split = myline.split("\t");
    		
    		for(int j = 0; j < split.length; j++) {
    			try {
    				int value = Integer.valueOf(split[j]);
    				if(split[j].matches("[0-9]+") && value != 0)
    					lineofmatrix.add(value);
    				else {
    					System.out.println("Illegal number.");
    					return false;
    				}    				
    			}catch(NumberFormatException e) {
    				System.out.println("Illegal symbol.");
    				return false;
    			}
    		}
    		matrix.add(lineofmatrix);
    		
    		if(len != split.length) {
    			System.out.println("It is not a matrix.");
    			return false;
    		}
    	}
    	
    	//检验矩阵是否满足行、列、对角线求和相等
    	int row = matrix.size(), col = row;    	
    	int[] sum = new int[row + col + 2];
    	for(int i = 0; i < row; i++) {//按行求和
    		for(int j = 0; j < col; j++) {
    			sum[i] += matrix.get(i).get(j);
    		}
    	}
    	for(int i = 0; i < col; i++) {//按列求和
    		for(int j = 0; j < row; j++) {
    			sum[row + i] += matrix.get(j).get(i);
    		}
    	}
    	int j = 0;
    	for(int i = 0; i < row; i++) {//主对角线求和
    		sum[row + col] += matrix.get(i).get(i);
    		j += 1;
    	}
    	j = row - 1;
    	for(int i = 0; i < row; i++) {//副对角线求和
    		sum[row + col + 1] += matrix.get(i).get(j);
    		j -= 1;
    	}
    	for (int i = 0; i < row + col + 1; i++) {
    		if(sum[i] != sum[i + 1])
    			return false;
    	}
    	
        return true;
        
    }
    
    public static boolean generateMagicSquare(int n) {//输入参数n，表示构造一个n*n的幻方
    	
    	try {
    		//注意n为0的特例，若不单独讨论，将在文件中不写入任何内容，但返回仍为true
    		if(n == 0) {
    			System.out.println("Even input.");
        		return false;
    		}
    		int magic[][] = new int[n][n];
    		int row = 0, col = n / 2, i, j, square = n * n;
    		for (i = 1; i <= square; i++) {//将1~n*n按从小到大的顺序依次填入
    			magic[row][col] = i;//填数时的起始位置为第一排中间格
    			if (i % n == 0)//每填完n个数，向下一格继续填数
    				row++;
    			else {//尚未填完n个数时，沿着副对角线方向向右上进行填数
    				if (row == 0)
    					row = n - 1;
    				else
    					row--;
    				if (col == (n - 1))
    					col = 0;
    				else
    					col++;
    			}
    		}
    		//修改后的代码不再在控制台输出幻方，而是将其写入指定文件
    		File file = new File("src\\P1\\txt\\6.txt");
    		BufferedWriter writer = null;
    		writer = new BufferedWriter(new FileWriter(file));
    		for(i = 0; i < n; i++) {
    			for(j = 0; j < n; j++)
    				writer.write(magic[i][j] + "\t");
    			writer.newLine();
    		}
    		writer.close();
    	}catch(NegativeArraySizeException e) {
    		System.out.println("Negative input.");
    		return false;
    	}catch(ArrayIndexOutOfBoundsException e) {
    		System.out.println("Even input.");
    		return false;
    	}catch(IOException e) {
    		System.out.println("File write error.");
    		return false;
    	}
    	
    	return true;
    	
    }

	public static void main(String[] args) {
		
        Boolean a = MagicSquare.isLegalMagicSquare("src\\P1\\txt\\1.txt");
        System.out.println(a+"\n");
        
        Boolean b = MagicSquare.isLegalMagicSquare("src\\P1\\txt\\2.txt");
        System.out.println(b+"\n");
        
        Boolean c = MagicSquare.isLegalMagicSquare("src\\P1\\txt\\3.txt");
        System.out.println(c+"\n");

        Boolean d = MagicSquare.isLegalMagicSquare("src\\P1\\txt\\4.txt");
        System.out.println(d+"\n");
        
        Boolean e = MagicSquare.isLegalMagicSquare("src\\P1\\txt\\5.txt");
        System.out.println(e+"\n");
        
        Boolean f = MagicSquare.generateMagicSquare(3);
        System.out.println(f+"\n");
        
        Boolean g = MagicSquare.generateMagicSquare(-1);
        System.out.println(g+"\n");
        
        Boolean h = MagicSquare.generateMagicSquare(0);
        System.out.println(h+"\n");
        
        Boolean i = MagicSquare.generateMagicSquare(4);
        System.out.println(i+"\n");
        
        Boolean j = MagicSquare.isLegalMagicSquare("src\\P1\\txt\\6.txt");
        System.out.println(j+"\n");

	}

}

