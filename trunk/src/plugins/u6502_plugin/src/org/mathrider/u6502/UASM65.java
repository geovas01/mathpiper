package org.mathrider.u6502;

import java.io.File;
import java.io.RandomAccessFile;
import java.io.FileWriter;
import java.io.IOException;                                                                                                 
//import org.gjt.sp.jedit.bsh.EvalError;
import java.util.HashMap;
import java.util.ArrayList;


public class UASM65
{
	
	private String error = "none";
	
	private File source_file;


	private int line_character;
	private int[] temp_add_mode = new int [5];
	private String lst_filename = "";// = new int [20];
	private int[] source_line = new int[132];
	private int[] hold_label = new int[50];
	private int[] hold_operator = new int[10];
	private int[] hold_operand = new int[132];
	private int[] hold_add_mode = new int[5];

	private int line_index,sym_tbl_index,op_tbl_index,end_flag;
	private int error_index,source_line_number,symbol_index; //return_code.
	private int location_counter,hold_location;
	private Integer temp_converted_number;

	private java.io.RandomAccessFile source_file_pointer;
	private FileWriter lst_file_ptr;

	private int no_source_flag,no_lc_flag,no_obj_flag,hold_obj_1,hold_obj_2,hold_obj_3;
	private int no_line_num_flag,print_error_index,lst_flag;
	private boolean pass2_flag;
	private int s_record_index,code_index,record_length;
	private String source_file_name = ""; //= new int [30];
	private int operand_index;

	private int EOF = -1;


	private String[] error_message = new String[]{
	                                     "",
	                                     "Invalid Character in label.",
	                                     "Symbol not defined.",
	                                     "Missing END directive or no CR after END.",
	                                     "Redefinition of a symbol.",
	                                     "",
	                                     "Operand expected.",
	                                     "Operator expected.",
	                                     "Value out of range.",
	                                     "Branch out of range.",
	                                     "",
	                                     "",
	                                     "Illegal digit in number.",
	                                     "Empty string not allowed",
	                                     "",
	                                     "",
	                                     "Invalid operand.",
	                                     "Invalid operator.",
	                                     "Label too long.",
	                                     "Invalid character in operator.",
	                                     "",
	                                     "Invalid character in operand.",
	                                     "Operand too long.",
	                                     "Missing trailing quote mark.",
	                                     "Missing number base indicator.",
	                                     "",
	                                     "",
	                                     "",
	                                     "",
	                                     "",
	                                     "Invalid addressing mode for operator.",
	                                     "",
	                                     "Empty character not allowed.",
	                                 };


	// Change to scripted objects.

	private class error_tbl
	{
		int line_number;
		int line_index;
		int error_number;
	}

	private class op_tbl
	{
		int[] operator = new int[5];
		int[] add_code = new int[5];
		int opcode;
		int num_bytes;
		int base_cycles;

		public op_tbl(String operator, String add_code, int opcode, int num_bytes, int base_cycles)
		{
			char[] op = operator.toCharArray();
			char[] add = add_code.toCharArray();
			
			for(int x = 0;x < op.length; x++)
			{
				this.operator[x] = (int) op[x];
			}

			
			for(int x = 0;x < add.length; x++)
			{
				this.add_code[x] = (int) add[x];
			}

			this.opcode = opcode;
			this.num_bytes = num_bytes;
			this.base_cycles = base_cycles;
		}
	}

	private class sym_tbl
	{
		int[] label = new int[50];
		int address;
	}

	private class s_rec
	{
		int record_length;
		int address;
		int[] code = new int[20];
	}
	
	private class Number
	{
		int number;
	}//end class.


	//struct op_tbl op_table[150];
	private op_tbl[] op_table = new op_tbl[130];
	
	//struct sym_tbl symbol_table[600];
	//private ArrayList symbol_table = new ArrayList();
	private sym_tbl[] symbol_table = new sym_tbl[600];

	
	//struct error_tbl error_table[70];
	private error_tbl[] error_table = new error_tbl[70];

	
	//struct s_rec s_record[386];
	//private ArrayList s_record = new ArrayList();
	private s_rec[] s_record = new s_rec[386];



	public UASM65()
	{
		super();
		
		//Initialize tables.  Note: this approached has much room for improvment.
		for(int x = 0; x<600; x++)
		{
			symbol_table[x] = new sym_tbl();
		}//end for.
		
		for(int x = 0; x<70; x++)
		{
			error_table[x] = new error_tbl();
		}//end for.	  s_record
		
		for(int x = 0; x<386; x++)
		{
			s_record[x] = new s_rec();
		}//end for.	  s_record
		
		
		
		//source_file = new File("c:/ted/checkouts/mathrider/src/plugins/u6502_plugin/src/scripts/test.asm");
		source_file = new File("c:/ted/checkouts/mathrider/src/plugins/u6502_plugin/src/scripts/umon65muvium.asm");

		initialize();
		
		read_operator_table();


	}//end constructor



	/************************************************************************
	   UASM65 V1.0 - Understandable Assembler for the 6500 series Microprocessor.
	   Copyright 1993 by Ted Kosan.
	   
	************************************************************************/





	private void initialize()
	{
		lst_flag=1;
		print_error_index = 1;
		s_record_index = 0;
		code_index=0;
		location_counter = 0;
		error_index = 0;
		sym_tbl_index=0;
		source_line_number=0;
		end_flag=0;
		line_index = 0;
		no_source_flag = 0;
		no_lc_flag = 0;
		no_obj_flag = 0;
		no_line_num_flag = 0;
		hold_obj_1=-1;
		hold_obj_2=-1;
		hold_obj_3=-1;
		wipe_line();
		error_table[0].line_number = 0; //Note: might need to add replacement code here.
	}// end initialize.
	
	
	//close_files.
	void closeFiles()
	{
		try
		{
			if(source_file_pointer != null)
			{
				source_file_pointer.close();
			}
		}
		catch(IOException e)
		{
			e.printStackTrace();
		}
		
		try
		{
			if(lst_file_ptr != null)
			{
				lst_file_ptr.close();
			}
		}
		catch(IOException e)
		{
			e.printStackTrace();
		}
	}//


	//{{{strcpy
	private void strcpy( int[] destination, int[] source)
	{
		//System.out.println(" XXXXXXX " + destination + source);
		//System.out.println(" XXXXXXX " + chars_to_string(destination) + chars_to_string(source));
		//returnError = destination;  Note: begin here.
		//throw new EvalError("hello",null,null);
		int index = 0;
		int c;
		do
		{
			c = source[index];
			destination[index] = c;
			index++;
		}while(c != '\0');
	}//end method.
	
	private void strcpy( int[] destination, String source)
	{
		byte[] bytes = source.getBytes();
		
		for(int x = 0; x < source.length(); x++)
		{
			destination[x] = bytes[x];
		}
		
	}
	
	
	private void strcpy( String destination, String source)
	{
		destination = new String(source);
	}
	//}}}
	
	//{{{strcat
	private int[] strcat( int[] one, int[] two)
	{
		String stringOne = chars_to_string(one);
		String stringTwo = chars_to_string(two);
		
		return(string_to_chars(stringOne.concat(stringTwo)));
		
	} // end method.
	
	
	private int[] strcat( int[] one, String two)
	{
		String stringOne = chars_to_string(one);
		
		
		return string_to_chars(stringOne.concat(two));
		
	}//end method.
	
	//}}}
	
	
	//{{{strcmp
	private int strcmp(int[] first, String second)
	{
		char[] string = second.toCharArray();
		int x = 0;
		do
			if(first[x] != (int) string[x])
			{
				return 1;
			}
		while( string[x++] != '\0' );
		
			
		//for(int x = 0; string[x] != '\0' ; x++)
		//{
		//	if(first[x] != (int) string[x])
		//	{
		//		return 1;
		//	}
		//}
		
		return 0;
		
	}//}}}
	
	
	//{{{strcmp
	private int strcmp(int[] first, int[] second)
	{
		
		int x = 0;
		do
			if(first[x] != second[x])
			{
				return 1;
			}
		while( second[x++] != '\0' );
			
			
		//for(int x = 0; second[x] != 0 ; x++)
		//{
		//	if(first[x] != second[x])
		//	{
		//		return 1;
		//	}
		//}
		
		return 0;
	}//}}}
	
	
	//{{{strchr
	private boolean strchr(int[] string, char character)
	{
		int x = 0;
		do
			if(string[x] == character )
			{
				return true;
			}
		while( string[x++] != '\0' );
		
		return false;
		
	}//}}}
	
	//{{{strlen
	private int strlen(int[] string)
	{   
		int x = 0;
		for(x = 0; string[x] != 0; x++)
		{
		}
		return x;
	}//}}}


	//{{{chars_to_string
	private String chars_to_string(int[] chars)
	{
		int stringEndIndex = 0;
		for(; chars[stringEndIndex] != 0; stringEndIndex++);
		
		return new String(chars,0,stringEndIndex);
	}//}}}
	
	
	
	//{{{string_to_chars
	private int[] string_to_chars(String string)
	{
		char[] chars = string.toCharArray();
		int[] ints = new int[150];
		int x = 0;
		for(; x < chars.length; x++)
		{
			ints[x] = (int) chars[x];
		}//end for.
		
		ints[x] = 0;
		
		return ints;
			
	}//}}}

	

	//{{{strstr
	private boolean strstr(int[] first, int[] second)
	{
		String string1 = chars_to_string(first);

		String string2 = chars_to_string(second);

		if(string1.indexOf(string2) != -1)
		{
			return true;
		}
		else
		{
			return false;
		}
	}//end method.
	//}}}


	//{{{fgetc
	private int fgetc()
	{
		try
		{
			return (int)source_file_pointer.read();
		}
		catch (IOException e)
		{
			e.printStackTrace();
		}
		return -1;
	}//
	//}}}


	//{{{toupper
	private int toupper(int c)
	{
		if(c >= 'a' && c <= 'z')
		{
			c = (int) c - 32;

		}
		return c;
	}//}}}
	
	
	//{{{ intArrayToString
	private String intArrayToString(int[] intArray)
	{
		
		int count;
		for(count=0; intArray[count] != 0; count++)
		{
		}
		
		return new String(intArray,0 ,count);
	}//}}}
	
	
	

	private boolean pass1()
	{

		pass2_flag=false;
		lst_flag = 1;
		strcpy(symbol_table[1].label,"XXX");

			if ( (source_file_pointer = open_file(source_file)) != null)
			{
				if(! open_lst_file())
				{
					return false;
				}
		//
		scan_lines(1);
		
		
				if (end_flag == 0)
				{
					log_error(3);
				}
		
		
				//Uncomment for debugging.
				System.out.println("Symbol table dump: ");
				for (int x=1;x<=sym_tbl_index;x++)
				{
					System.out.printf("\n%d %s %x",x,intArrayToString(symbol_table[x].label), symbol_table[x].address);
		
				}
		
		
		
		
				if (error_index == 0 && end_flag == 1)
				{
					System.out.println("\n\nPass1 0 errors\n");
					return(true);
				}
				else
				{
					System.out.printf("\n\nPass1 %d errors.\n",error_index);
		
					
					
					//Error table dump.  Note: uncomment for debugging.
					int x = 0;
					for (x=1;x<=error_index;x++)
					{
						System.out.printf("\nline#: %d    ",error_table[x].line_number);
						System.out.printf("error number: %d",error_table[x].error_number);
					}
					
					
					
		
					System.out.println("\n");
					
					try
					{
						source_file_pointer.close();
						lst_file_ptr.close();
					}
					catch(IOException e)
					{
						e.printStackTrace();
					}
					return(false);
				}
		
			}
			else
			{
				System.out.printf("\nFile not found");
				return(false);
			}
		
	}//}}}End pass1.
	//

	//
	//
	////{{{ Pass 2
	///************************************************************************
	//   UASM65 V1.0 - Understandable Assembler for the 6500 series Microprocessor.
	//   Copyright 1993 by Ted Kosan.
	//
	//************************************************************************/
	//
	//
	boolean pass2()
	{
		pass2_flag=true;
		//fseek(source_file_pointer,0,SEEK_SET); 
		
		try
		{
			source_file_pointer.seek(0);
		}
		catch(IOException e)
		{
			e.printStackTrace();
		}
		
		initialize();
		scan_lines(2);
	
		if (error_index == 0 && end_flag == 1)
		{
			System.out.println("\nPass2 0 errors\n\n");
			return(true);
		}
		else
		{
			System.out.printf("\nPass2 %d errors.\n\n",error_index);
	/*
			for (x=1;x<=error_index;x++)
			{
				printf("\nline#: %d    ",error_table[x].line_number);
				printf("line index: %d    ",error_table[x].line_index);
				printf("error number: %d",error_table[x].error_number);
			}
	*/
			System.out.println("\n");
			//fclose(source_file_pointer);
			try
			{
				source_file_pointer.close();
			}
			catch(IOException e)
			{
				e.printStackTrace();
			}
	
			return(false);
		}
	
	
	}
	


	//{{{scan_lines
	private void scan_lines(int pass_flag)
	{
		while ((line_character=fgetc()) != EOF)
		{

			
			if (line_character == 13)
			{
				int charRead = fgetc();
				if ( charRead == 10)
				{
					//source_line[line_index]=0;
					//parse_line();
					//line_index = 0;
					//wipe_line();
					
					source_line[line_index]=0;
					boolean return_code=parse_line();
					if (return_code != false && pass_flag == 1)
					{
						interpret_line_pass1();
					}
					else if (return_code != false && pass_flag ==2)
					{
						interpret_line_pass2();
					}
					else if (return_code == false && pass_flag == 2)
					{
						no_lc_flag=1;
						print_line();
					}
                	
					line_index = 0;
					wipe_line(); 

				}
				else
				{  
					log_error(5);
					return;
					//try
					//{
					//	source_file_pointer.unread(charRead); //Note: this might throw a not supported exception.
					//}
					//catch(IOException e)
					//{
					//	e.printStackTrace();
					//}
                    //
					//source_line[line_index]=0;
					//parse_line();
					//line_index = 0;
					//wipe_line();

				}
			}
			else if (line_character == 10)
			{
				source_line[line_index]=0;
				boolean return_code=parse_line();
				if (return_code != false && pass_flag == 1)
				{
					interpret_line_pass1();
				}
				else if (return_code != false && pass_flag ==2)
				{
					//interpret_line_pass2();
				}
				else if (return_code == false && pass_flag == 2)
				{
					no_lc_flag=1;
					print_line();
				}

				line_index = 0;
				wipe_line();     

			}
			else
			{
				source_line[line_index] = line_character;
				line_index++;
			}

		}
	}//end method. }}}


	//{{{ parse_line
	private boolean parse_line()
	{
		int error_number = 0;
		hold_label[0]=0;
		hold_operator[0]=0;
		hold_operand[0]=0;
		hold_add_mode[0]=0;
		line_index = 0;
		source_line_number++;

		if (source_line[0] == ';' || source_line[0] == 0)
		{
			return(false);
		}
		if (scan_to_EOL())
		{
			return(false);
		}
		else if (check_label_character(source_line[0])) /* Check for label.*/
		{
			if ((error_number=scan_label()) != 0)
			{
				log_error(error_number);
				return(false);
			}

		}

		while(source_line[line_index] ==' ' || source_line[line_index] == 9)
		{
			line_index++;
			if (line_index >= 125)
			{
				log_error(7);
				return(false);
			}
			else if (source_line[line_index] == 0)
			{
				return(true);
			}
		}

		if ((error_number = scan_operator()) != 0)
		{
			log_error(error_number);
			return(false);
		}


		if (!valid_mnemonic_scan())
		{
			log_error(17);
			return(false);
		}


		if (strcmp(temp_add_mode,"IMP\0")!=0)
		{

			while(source_line[line_index] ==' ' || source_line[line_index] == 9)
			{
				line_index++;
				if (line_index >= 125)
				{
					log_error(6);
					return(false);
				}
				else if (source_line[line_index] == 0 || source_line[line_index] == ';')
				{
					log_error(6);
					return(false);
				}
			}

			if ((error_number = scan_operand()) != 0)
			{
				log_error(error_number);
				return(false);
			}
		}
		else
		{
			strcpy(hold_operand,"       \0");
			strcpy(hold_add_mode,"IMP\0");
		}

		
		//Note: uncomment for debugging.
		//	if (! pass2_flag)
		//	{
		//		System.out.printf("%d : %x : %s : %s : %s : %s\n\n",source_line_number, location_counter, intArrayToString(hold_label), intArrayToString(hold_operator), intArrayToString(hold_operand), intArrayToString(hold_add_mode) );
		//	}
		

		return(true);
	}//}}}



	//
	//{{ scan_label
	private int scan_label()
	{
		while (source_line[line_index] != ' ' && source_line[line_index] != 9)
		{
			if (check_label_character(source_line[line_index]))
			{
				hold_label[line_index] = source_line[line_index];
			}
			else
			{
				return(1);
			}
	
			line_index++;
			if (line_index > 50)
			{
				return(18);
			}
		}
		hold_label[line_index] = 0;
		hold_location = location_counter;
		str_to_upper(hold_label);
		return(0);
	}//}}}
	//

	
	
	//{{{scan_operator
	private int scan_operator()
	{
		int local_line_index;
		local_line_index = 0;
		while (source_line[line_index] != ' '&& source_line[line_index] != 9 && source_line[line_index] != 0)
		{
			if (check_operator_character(source_line[line_index]))
			{
				hold_operator[local_line_index] = source_line[line_index];
			}
			else
			{
				return(19);
			}

			line_index++;
			local_line_index++;
			if (local_line_index > 10)
			{
				return(20);
			}
		}
		hold_operator[local_line_index] = 0;
		str_to_upper(hold_operator);
		return(0);
	}//}}}

	//
	//{{{valid_mnemonic_scan
	private boolean valid_mnemonic_scan()
	{
		op_tbl_index = 0;
		while (true)
		{
			if ((strcmp(op_table[op_tbl_index].operator,"XXX\0"))== 0)
			{
				return(false);
			}
			else if ((strcmp(op_table[op_tbl_index].operator,hold_operator))== 0)
			{
				strcpy(temp_add_mode,op_table[op_tbl_index].add_code);
				return(true);
			}
			op_tbl_index++;
		}
		//return(false);
	}//}}}
	

	//
	//{{{scan_operand
	private int scan_operand()
	{
		int local_line_index;
		local_line_index = 0;
		if (source_line[line_index] == 34)
		{
			hold_operand[0]= 34;
			local_line_index++;
			line_index++;
			while (source_line[line_index] != 34)
			{
				if (check_operand_character(source_line[line_index]))
				{
					hold_operand[local_line_index] = source_line[line_index];
				}
				else
				{
					return(21);
				}
	
				line_index++;
				local_line_index++;
				if (local_line_index > 90)
				{
					return(22);
				}
			}
			hold_operand[local_line_index] = 34;
			hold_operand[local_line_index+1] = 0;
			return(0);
		}
		else
		{
			while (source_line[line_index] != ' '&& source_line[line_index] != 9 && source_line[line_index] != ';'&& source_line[line_index] != 0)
			{
				if (check_operand_character(source_line[line_index]))
				{
					hold_operand[local_line_index] = source_line[line_index];
				}
				else
				{
					return(21);
				}
	
				line_index++;
				local_line_index++;
				if (local_line_index > 90)
				{
					return(22);
				}
			}
			hold_operand[local_line_index] = 0;
			if (hold_operand[1] != 39)
			{
				str_to_upper(hold_operand);
			}
			parse_operand();
			return(0);
		}
	
	}//end method }}}

	//{{{parse_operand
	boolean parse_operand()
	{
	int local_index;
	local_index = 0;
		if (hold_operand[0] == '#')
		{
			strcpy(hold_add_mode,"IMM\0");
		}
		else if ((hold_operand[0] == 'A' || hold_operand[0] == 'a') && hold_operand[1] == 0)
		{
			strcpy(hold_add_mode,"ACC\0");
		}
		else if (hold_operand[0] == '(')
		{
		 	local_index++;
		 	while (hold_operand[local_index] != 0)
		 	{
		 		if (hold_operand[local_index] == ',')
		 		{
		 			if (hold_operand[local_index+1] == 'x' || hold_operand[local_index+1] == 'X')
		 			{
		 				strcpy(hold_add_mode,"IXR\0");
		 				return(true);
		 			}
		 			else
		 			{
		 				log_error(16);
		 				return(false);
		 			}
		 		}
		 		else if (hold_operand[local_index] == ')')
		 		{
		 			local_index++;
		 			while (hold_operand[local_index] != 0)
		 			{
		 				if (hold_operand[local_index] == ',')
		 				{
		 					if (hold_operand[local_index+1] == 'y' || hold_operand[local_index+1] == 'Y')
		 					{
		 						strcpy(hold_add_mode,"IRX\0");
		 						return(true);
		 					}
		 					else
		 					{
		 						log_error(16);
		 						return(false);
		 					}
		 				}
		 				else
		 				{
		 					log_error(16);
		 					return(false);
		 				}

		 			}
		 			strcpy(hold_add_mode,"IND\0");
		 			return(true);
		 		}
		 		local_index++;
		 	}
			log_error(16);
			return(false);
		}
		else if (strcmp(hold_operand,"\0")==0)
		{
			log_error(6);
			return(false);
		}
		else
		{
		 	while (hold_operand[local_index] != 0)
		 	{
		 		if (hold_operand[local_index] == ',')
		 		{
		 			if (hold_operand[local_index+1] == 'x' || hold_operand[local_index+1] == 'X')
		 			{
		 				strcpy(hold_add_mode,"ABX\0");
		 				return(true);
		 			}
		 			else if (hold_operand[local_index+1] == 'y' || hold_operand[local_index+1] == 'Y')
		 			{
		 				strcpy(hold_add_mode,"ABY\0");
		 				return(true);
		 			}
		 		}
		 		local_index++;
		 	}
		 	if (strcmp(op_table[op_tbl_index].add_code,"REL\0")==0)
		 	{
		 		strcpy(hold_add_mode,"REL\0");
		 		return(true);
		 	}
		 	else
		 	{
		 		strcpy(hold_add_mode,"ABS\0");
		 	}
		}
		return(true);
	}//}}}

	
	
	
	//{{{interpret_line_pass1()
	boolean interpret_line_pass1()
	{
		
		if (hold_label[0] != 0 && (strcmp(hold_operator,"EQU\0")!=0))
		{
			log_label();
		}
	
	
		if (strcmp(hold_operator,"LON\0")==0)
		{
			lst_flag=1;
		}
		else if (strcmp(hold_operator,"LOF\0")==0)
		{
			lst_flag=0;
		}
		else if (strcmp(hold_operator,"ORG\0")==0)
		{
			//Integer tmp_converted_number = new Integer(0);
			Number tmp_converted_number = new Number();
			if (convert_to_number(hold_operand, tmp_converted_number)) //,&temp_converted_number))
			{
				this.temp_converted_number = tmp_converted_number.number;
				if (tmp_converted_number.number >=0 && tmp_converted_number.number <=65535)
				{
					location_counter=tmp_converted_number.number;
				}
				else
				{
					log_error(8);
					return(false);
				}
			}
			else
			{
				log_error(16);
				return(false);
			}
		}
		else if (strcmp(hold_operator,"END\0")==0)
		{
			end_flag=1;
		}
		else if (strcmp(hold_operator,"*\0")==0)
		{
		;
		}
		else if (strcmp(hold_operator,"EQU\0")==0)
		{
			//Integer tmp_converted_number = new Integer(0);
			Number tmp_converted_number = new Number();
			if (convert_to_number(hold_operand, tmp_converted_number)) //,&temp_converted_number))
			{
				this.temp_converted_number = tmp_converted_number.number;
				log_symbol();
			}
			else
			{
				log_error(16);
				return(false);
			}
		}
		else if (strcmp(hold_operator,"DBT\0")==0)
		{
			if (hold_operand[0] == 34) /* Check for ASCII by finding " */
			{
				count_ascii_characters();
			}
			else if (strchr(hold_operand,'('))// && strchr(hold_operand,'('))
			{
				calculate_duplicates();
			}
			else if (strchr(hold_operand,',')) //Note: was strchr
			{
				count_bytes();
			}
			else if (strcmp(hold_operand,"?\0")==0)
			{
				location_counter++;
			}
			else if ((strchr(hold_operand,'D') || strchr(hold_operand,'B') || strchr(hold_operand,'H')) && hold_operand[0] != '#')
			{
				location_counter++;
			}
			else if (hold_operand[0] == '#')
			{
				location_counter++;
			}
			else
			{
				log_error(16);
				return(false);
			}
		}
		else if (strcmp(hold_operator,"DWD\0")==0)
		{
			if (strchr(hold_operand,','))
			{
				count_words();
			}
			else if (strcmp(hold_operand,"?\0")==0)
			{
				location_counter++;
				location_counter++;
			}
	
		}
		else
		{
			valid_mnemonic_scan();
			
			while (strcmp(hold_operator, op_table[op_tbl_index].operator)==0)
			{
				if(strcmp(hold_add_mode,op_table[op_tbl_index].add_code)==0)
				{
					location_counter=location_counter + op_table[op_tbl_index].num_bytes;
					return(true);
				}
				op_tbl_index++;
			}
			log_error(30);
			return(false);
		}
		return(true); //Note: this line should never execute.
	}//}}}


	//{{{interpret_line_pass2
	boolean interpret_line_pass2()                  
	{
		int operand_index = 0;
		int local_index = 0; 
		int[] ascii_hold = new int[50]; //char ascii_hold[50];
		//int number = 0;
		Number number = new Number();
		
		if (strcmp(hold_operator,"LON\0")==0)
		{
			no_obj_flag = 1;
			lst_flag=1;
			print_line();
		}
		else if (strcmp(hold_operator,"LOF\0")==0)
		{
			no_obj_flag = 1;
			print_line();
			lst_flag=0;
		}
		else if (strcmp(hold_operator,"ORG\0")==0)
		{
			//Integer tmp_converted_number = new Integer(0);
			Number tmp_converted_number = new Number();
			if (convert_to_number(hold_operand, tmp_converted_number)) // ,&temp_converted_number))
			{
				this.temp_converted_number = tmp_converted_number.number;
				if (tmp_converted_number.number >=0 && tmp_converted_number.number <=65535)
				{
					location_counter=tmp_converted_number.number;
					no_obj_flag = 1;
					print_line();
					return(true);
				}
				else
				{
					log_error(8);
					return(false);
				}
			}
			else
			{
				log_error(16);
				return(false);
			}
		}
		else if (strcmp(hold_operator,"END\0")==0)
		{
			no_lc_flag=1;
			print_line();
			end_flag=1;
		}
		else if (strcmp(hold_operator,"*\0")==0)
		{
			no_obj_flag = 1;
			print_line();
		}
		else if (strcmp(hold_operator,"EQU\0")==0)
		{
			no_obj_flag = 1;
			print_line();
		}
		else if (strcmp(hold_operator,"DBT\0")==0)
		{
			if (hold_operand[0] == 34) /* Check for ASCII by finding " */
			{
				process_ascii_characters();
			}
			else if (strchr(hold_operand,'(') && strchr(hold_operand,'('))
			{
				process_byte_duplicates();
			}
			else if (strchr(hold_operand,','))
			{
				process_bytes();
			}
			else if (strcmp(hold_operand,"?\0")==0)
			{
				hold_obj_1 = 0;
				print_line();
				location_counter++;
			}
			else if ((strchr(hold_operand,'D') || strchr(hold_operand,'B') || strchr(hold_operand,'H')) && hold_operand[0] != '#')
			{
				local_index = 0;
				operand_index = 0;
	
				while (hold_operand[operand_index] != 0)
				{
					ascii_hold[local_index] = hold_operand[operand_index];
					local_index++;
					operand_index++;
					if(operand_index > 100)
					{
						log_error(22);
						return(false);
					}
				}
				ascii_hold[local_index] = 0;
	
				if ( is_number(ascii_hold) )
				{
					if (convert_to_number(ascii_hold,number))
					{
						if (number.number >= 0 && number.number <= 255)
						{
							hold_obj_1 = number.number & 255;
							print_line();
							location_counter++;
							return(true);
						}
						else
						{
							log_error(8);
							return(false);
						}
					}
					else
					{
						log_error(16);
						return(false);
					}
				}
	
			}
	
	
			else if (hold_operand[0] == '#')
			{
			local_index = 0;
			operand_index = 0;
	
			operand_index++;
	
			while (hold_operand[operand_index] != 0 && hold_operand[operand_index] != '<' && hold_operand[operand_index] != '>')
			{
				ascii_hold[local_index] = hold_operand[operand_index];
				local_index++;
				operand_index++;
				if(operand_index > 100)
				{
					log_error(22);
					return(false);
				}
			}
			ascii_hold[local_index] = 0;
	
			if ( is_number(ascii_hold) )
			{
				if (convert_to_number(ascii_hold,number))
				{
					if (number.number >= 0 && number.number <=255)
					{
						hold_obj_1 = number.number & 255;
						return(true);
					}
					else
					{
						log_error(8);
						return(false);
					}
				}
				else
				{
					log_error(16);
					return(false);
				}
			}
			else
			{
				if (! scan_for_symbol(ascii_hold))
				{
					if (hold_operand[operand_index] == '<')
					{
						hold_obj_1 = (symbol_table[symbol_index].address & 255);
						print_line();
						location_counter++;
						return(true);
					}
					else if (hold_operand[operand_index] == '>')
					{
						hold_obj_1 = ((symbol_table[symbol_index].address >> 8) & 255);
						print_line();
						location_counter++;
						return(true);
					}
					else
					{
						hold_obj_1 = (symbol_table[symbol_index].address & 255);
						print_line();
						location_counter++;
						return(true);
					}
				}
				else
				{
					log_error(2);
					return(false);
				}
			}
	
	
			}
			else
			{
				log_error(16);
				return(false);
			}
	
		}
		else if (strcmp(hold_operator,"DWD\0")==0)
		{
			if (hold_operand[0] == 34) /* Check for ASCII by finding " */
			{
				log_error(33);
				return(false);
			}
			else if (strchr(hold_operand,'('))// && strchr(hold_operand,'('))
			{
				process_word_duplicates();
			}
			else if (strchr(hold_operand,','))
			{
				process_words();
			}
			else if (strcmp(hold_operand,"?\0")==0)
			{
				hold_obj_1 = 0;
				hold_obj_2 = 0;
				print_line();
	
				location_counter+=2;
			}
			else
			{
				log_error(6);
				return(false);
			}
	
		}
		else
		{
			valid_mnemonic_scan();
			while (strcmp(hold_operator,op_table[op_tbl_index].operator)==0)
			{
				if(strcmp(hold_add_mode,op_table[op_tbl_index].add_code)==0)
				{
					hold_obj_1 = op_table[op_tbl_index].opcode;
					if (parse_operand_pass2())
					{
						print_line();
						location_counter=location_counter + op_table[op_tbl_index].num_bytes;
						return(true);
					}
					else
					{
						log_error(16);
						return(false);
					}
	
				}
				op_tbl_index++;
			}
			log_error(16);
			return(false);
		}
		
		return false; //Note: this line should never execute.
	
	}//}}}
	
	
	//{{{parse_operand_pass2()
	boolean parse_operand_pass2()
	{
		//char hold_add_code[5],ascii_hold[120];
		int[] hold_add_code = new int[5];
		int[] ascii_hold = new int[120];
		
		int local_index = 0;
		int operand_index = 0;
		
		int next_inst_add = 0;
		
		//int number = 0;
		Number number = new Number();
		
		int temp_number = 0;
		int offset = 0;
		local_index = 0;
		operand_index = 0;
	
	
		strcpy (hold_add_code,op_table[op_tbl_index].add_code);
	
		if (strcmp(hold_add_code,"IMP\0")==0)
		{
			return(true);
		}
		if (strcmp(hold_add_code,"ACC\0")==0)
		{
			if (strcmp(hold_operand,"A\0") == 0 || strcmp(hold_operand,"a") == 0)
				{
					return(true);
				}
				else
				{
					log_error(16);
					return(false);
				}
		}
		else if (strcmp(hold_add_code,"IMM\0") == 0)
		{
			operand_index++;
	
			if (hold_operand[operand_index] == 39)
			{
				operand_index++;
				if (check_operand_character(hold_operand[operand_index]) && hold_operand[operand_index+1] == 39)
				{
					hold_obj_2 = hold_operand[operand_index];
					return(true);
	
				}
				else
				{
					log_error(16);
					return(false);
				}
	
			}
	
			while (hold_operand[operand_index] != 0 && hold_operand[operand_index] != '<' && hold_operand[operand_index] != '>')
			{
				ascii_hold[local_index] = hold_operand[operand_index];
				local_index++;
				operand_index++;
				if(operand_index > 100)
				{
					log_error(22);
					return(false);
				}
			}
			ascii_hold[local_index] = 0;
	
			if ( is_number(ascii_hold) )
			{
				if (convert_to_number(ascii_hold,number))
				{
					if (number.number >= 0 && number.number <=255)
					{
						hold_obj_2 = number.number & 255;
						return(true);
					}
					else
					{
						log_error(8);
						return(false);
					}
				}
				else
				{
					log_error(16);
					return(false);
				}
			}
			else
			{
				if (! scan_for_symbol(ascii_hold))
				{
					if (hold_operand[operand_index] == '<')
					{
						hold_obj_2 = (symbol_table[symbol_index].address & 255);
						return(true);
					}
					else if (hold_operand[operand_index] == '>')
					{
						hold_obj_2 = ((symbol_table[symbol_index].address >> 8) & 255);
						return(true);
					}
					else
					{
						hold_obj_2 = (symbol_table[symbol_index].address & 255);
						return(true);
					}
				}
				else
				{
					log_error(2);
					return(false);
				}
			}
		}
		else if (strcmp(hold_add_code,"ABS\0") == 0)
		{
			while (hold_operand[operand_index] != 0 && hold_operand[operand_index] != '+')
			{
				ascii_hold[local_index] = hold_operand[operand_index];
				local_index++;
				operand_index++;
				if(operand_index > 100)
				{
					log_error(22);
					return(false);
				}
			}
			ascii_hold[local_index] = 0;
	
			if ( is_number(ascii_hold) )
			{
				if (convert_to_number(ascii_hold,number))
				{
					if (number.number >= 0 && number.number <= 65535)
					{
						hold_obj_2 = number.number & 255;
						hold_obj_3 = ((number.number >> 8) & 255);
						return(true);
					}
					else
					{
						log_error(8);
						return(false);
					}
				}
				else
				{
					log_error(16);
					return(false);
				}
			}
			else
			{
				if (! scan_for_symbol(ascii_hold))
				{
					if (hold_operand[operand_index] == '+')
					{
						local_index = 0;
						operand_index++;
						while (hold_operand[operand_index] != 0)
						{
							ascii_hold[local_index] = hold_operand[operand_index];
							local_index++;
							operand_index++;
							if(operand_index > 100) /* Be aware of overrun error */
							{
								log_error(22);
								return(false);
							}
						}
						ascii_hold[local_index] = 0;
						if ( is_number(ascii_hold) )
						{
							if (convert_to_number(ascii_hold,number))
							{
								if (number.number >= 0 && number.number <= 65535)
								{
									temp_number = symbol_table[symbol_index].address + number.number;
									hold_obj_2 = temp_number & 255;
									hold_obj_3 = ((temp_number >> 8) & 255);
									return(true);
								}
								else
								{
									log_error(8);
									return(false);
								}
							}
							else
							{
								log_error(16);
								return(false);
							}
						}
	
					}
					else
					{
						hold_obj_2 = symbol_table[symbol_index].address & 255;
						hold_obj_3 = ((symbol_table[symbol_index].address >> 8) & 255);
						return(true);
					}
				}
				else
				{
					log_error(2);
					return(false);
				}
			}
	
		}
		else if (strcmp(hold_add_code,"ABX\0") == 0 || strcmp(hold_add_code,"ABY\0") == 0)
		{
			while (hold_operand[operand_index] != 0 && hold_operand[operand_index] != ',')
			{
				ascii_hold[local_index] = hold_operand[operand_index];
				local_index++;
				operand_index++;
				if(operand_index > 100)
				{
					log_error(22);
					return(false);
				}
			}
			ascii_hold[local_index] = 0;
	
			if ( is_number(ascii_hold) )
			{
				if (convert_to_number(ascii_hold,number))
				{
					if (number.number >= 0 && number.number <= 65535)
					{
						hold_obj_2 = number.number & 255;
						hold_obj_3 = ((number.number >> 8) & 255);
						return(true);
					}
					else
					{
						log_error(8);
						return(false);
					}
				}
				else
				{
					log_error(16);
					return(false);
				}
			}
			else
			{
				if (! scan_for_symbol(ascii_hold))
				{
					hold_obj_2 = symbol_table[symbol_index].address & 255;
					hold_obj_3 = ((symbol_table[symbol_index].address >> 8) & 255);
					return(true);
				}
				else
				{
					log_error(2);
					return(false);
				}
			}
	
		}
		else if (strcmp(hold_add_code,"IRX\0") == 0 || strcmp(hold_add_code,"IXR\0") == 0 || strcmp(hold_add_code,"IND\0") == 0)
		{
			operand_index++;
			while (hold_operand[operand_index] != ',' && hold_operand[operand_index] != ')')
			{
				ascii_hold[local_index] = hold_operand[operand_index];
				local_index++;
				operand_index++;
				if(operand_index > 100)
				{
					log_error(22);
					return(false);
				}
			}
			ascii_hold[local_index] = 0;
	
			if ( is_number(ascii_hold) )
			{
				if (convert_to_number(ascii_hold,number))
				{
					if (strcmp(hold_add_code,"IND\0") == 0)
					{
						if (number.number >= 0 && number.number <= 65535)
						{
							hold_obj_2 = number.number & 255;
							hold_obj_3 = ((number.number >> 8) & 255);
							return(true);
						}
						else
						{
							log_error(8);
							return(false);
						}
					}
					else
					{
						if (number.number >= 0 && number.number <= 255)
						{
							hold_obj_2 = number.number & 255;
							return(true);
						}
						else
						{
							log_error(8);
							return(false);
						}
					}
				}
				else
				{
					log_error(16);
					return(false);
				}
			}
			else
			{
				if (! scan_for_symbol(ascii_hold))
				{
					if (strcmp(hold_add_code,"IND\0") == 0)
					{
						if (symbol_table[symbol_index].address >= 0 && symbol_table[symbol_index].address <= 65535)
						{
							hold_obj_2 = symbol_table[symbol_index].address & 255;
							hold_obj_3 = ((symbol_table[symbol_index].address >> 8) & 255);
							return(true);
						}
						else
						{
							log_error(8);
							return(false);
						}
					}
					else
					{
						if (symbol_table[symbol_index].address >= 0 && symbol_table[symbol_index].address <= 255)
						{
							hold_obj_2 = symbol_table[symbol_index].address & 255;
							return(true);
						}
						else
						{
							log_error(8);
							return(false);
						}
					}
				}
				else
				{
					log_error(2);
					return(false);
				}
			}
	
		}
		else if (strcmp(hold_add_code,"REL\0") == 0)
		{
			next_inst_add = location_counter;
			next_inst_add = next_inst_add + op_table[op_tbl_index].num_bytes;
			while (hold_operand[operand_index] != 0)
			{
				ascii_hold[local_index] = hold_operand[operand_index];
				local_index++;
				operand_index++;
				if(operand_index > 100)
				{
					log_error(22);
					return(false);
				}
			}
			ascii_hold[local_index] = 0;
	
			if ( is_number(ascii_hold) )
			{
					log_error(34);
					return(false);
			}
			else
			{
				if (! scan_for_symbol(ascii_hold))
				{
					offset = symbol_table[symbol_index].address - next_inst_add;
					if (offset <= 127 && offset >= -128)
					{
						hold_obj_2 = offset & 255;
						return(true);
					}
					else
					{
						log_error(9);
						return(false);
					}
				}
				else
				{
					log_error(2);
					return(false);
				}
			}
	
		}
		else
		{
			log_error(16);
			return(false);
		}
		
		return false; //Note: this line should never execute.
	
	}//}}}
	
	
	

	//{{{scan_for_symbol
	boolean scan_for_symbol(int symbol[])
	{
		symbol_index = 0;
		while(true)
		{
			if (strcmp(symbol_table[symbol_index].label,symbol)==0)
			{
				return(false);
			}
			else if (strcmp(symbol_table[symbol_index].label,"XXX\0")==0)
			{
				return(true);
			}
			symbol_index++;
		}
	}// }}}

	//
	////{{{ UUtility
	///************************************************************************
	//   UASM65 V1.0 - Understandable Assembler for the 6500 series Microprocessor.
	//   Copyright 1993 by Ted Kosan.
	//
	//************************************************************************/
	//
	//{{{convert_to_number
	boolean convert_to_number(int string_form[], Number number_form)//,unsigned long int *number_form)
	{
		int number_base = 0;
		int number_base_position=0; 
		boolean return_code = false;
		int result=0;
	
		number_base_position = strlen(string_form)-1;
		number_base = string_form[number_base_position];
		string_form[number_base_position] = 0;
	
		if (number_base == 'B' || number_base == 'b')
		{
			return_code = bin_to_integer(string_form, number_form);
		}
		else if (number_base == 'D' || number_base == 'd')
		{
			return_code = dec_to_integer(string_form, number_form);
		}
		else if (number_base == 'H' || number_base == 'h')
		{
			return_code = hex_to_integer(string_form, number_form);
		}
		else
		{
			log_error(24);
			return_code = false;
		}
	
		//*number_form = *number_form & 65535;
		number_form.number = number_form.number & 65535;
		
		return (return_code);
	
	} //}}}
	
	
	
	//{{{expand
	int expand(int digit, int places, int base)
	{
		int answer,multiplier;
		int iterations;
		multiplier = 1;
		for (iterations = 1;iterations <= places ;iterations++)
		{
			multiplier=multiplier*base;
		}
	
		answer=digit*multiplier;
	
		return(answer);
	} //}}}
	
	//{{{ dec_to_integer
	boolean dec_to_integer(int string_form[], Number number_form)//,unsigned long int *number_form)
	{
		int char_position, number, digit, string_form_length, a;
	
		char_position = strlen(string_form)-1;
		string_form_length = char_position;
		number_form.number = 0;
		;
	
		while (char_position >= 0)
		{
	
			if ((string_form[char_position] >= '0' && string_form[char_position] <= '9'))
			{
				digit = string_form[char_position]-48;
			}
			else
			{
				log_error(12);
				return(false);
			}
	
			a=expand(digit,string_form_length - char_position,10);
			//*number_form = *number_form + a;
			number_form.number += a;
			
			char_position--;
		}
	
		return(true);
	}//}}}


	//{{{hex_to_integer
	boolean hex_to_integer(int string_form[], Number number_form)//,unsigned long int *number_form)
	{
		int char_position, number, digit, string_form_length,a;
	
		char_position = strlen(string_form)-1;
		string_form_length = char_position;
		number_form.number = 0;

	
		while (char_position >= 0)
		{
	
			if ((string_form[char_position] >= '0' && string_form[char_position] <= '9'))
			{
				digit = string_form[char_position]-48;
			}
			else if ((string_form[char_position] >= 'A' && string_form[char_position] <= 'F'))
			{
				digit = string_form[char_position]-55;
			}
			else if ((string_form[char_position] >= 'a' && string_form[char_position] <= 'f'))
			{
				digit = string_form[char_position]-87;
			}
			else
			{
				log_error(12);
				return(false);
			}
	
			a=expand(digit,string_form_length - char_position,16);
			//*number_form = *number_form + a;
			number_form.number += a;
			
			char_position--;
		}
	
		return(true);
	}//}}}

	
	
	//{{{ bin_to_integerr
	boolean bin_to_integer(int string_form[], Number number_form)//,unsigned long int *number_form)
	{
		int char_position, number, digit, string_form_length,a;
	
		char_position = strlen(string_form)-1;
		string_form_length = char_position;
		number_form.number = 0;

	
		while (char_position >= 0)
		{
	
			if ((string_form[char_position] >= '0' && string_form[char_position] <= '1'))
			{
				digit = string_form[char_position]-48;
			}
			else
			{
				log_error(12);
				return(false);
			}
	
			a=expand(digit,string_form_length - char_position,2);
			//*number_form = *number_form + a;
			number_form.number += a;
			char_position--;
		}
	
		return(true);
	}  //}}}
	//
	
	//{{{str_to_upper
	private void str_to_upper(int[] string)
	{
		int index;
		index=0;
		while (string[index] != 0)
		{
			string[index]=toupper(string[index]);
			index++;
		}
	}//}}}

	
	
	
	//{{{is_number
	boolean is_number(int ascii_hold[])
	{
		int number_base_position = 0;
		int char_pos = 0;
		int number_base = 0;
		//ascii_hold_copy[20];
		int[] ascii_hold_copy = new int[20];
	
		if (ascii_hold[0] < '0' || ascii_hold[0] > '9')
			{
				return(false);
			}
	
		strcpy(ascii_hold_copy,ascii_hold);
		number_base_position = strlen(ascii_hold_copy)-1;
		number_base = ascii_hold_copy[number_base_position];
		ascii_hold_copy[number_base_position] = 0;
		char_pos = strlen(ascii_hold_copy)-1;
	
		if (number_base == 'B' || number_base == 'b')
		{
			while (char_pos >= 0)
			{
				if (ascii_hold_copy[char_pos] < '0' || ascii_hold_copy[char_pos] > '1')
				{
					return(false);
				}
				char_pos--;
			}
			return(true);
		}
		else if (number_base == 'D' || number_base == 'd')
		{
			while (char_pos >= 0)
			{
				if (ascii_hold_copy[char_pos] < '0' || ascii_hold_copy[char_pos] > '9')
				{
					return(false);
				}
				char_pos--;
			}
			return(true);
		}
		else if (number_base == 'H' || number_base == 'h')
		{
			while (char_pos >= 0)
			{
				if (ascii_hold_copy[char_pos] >= '0' && ascii_hold_copy[char_pos] <= '9')
				{
				}
				else if (ascii_hold_copy[char_pos] >= 'A' && ascii_hold_copy[char_pos] <= 'Z')
				{
				}
				else if (ascii_hold_copy[char_pos] >= 'a' && ascii_hold_copy[char_pos] <= 'z')
				{
				}
				else
				{
					return(false);
				}
				char_pos--;
			}
			return(true);
		}
		else
		{
			return(false);
		}

	}//}}}
	
	
	
	//{{{check_operator_character
	private boolean check_operator_character(int character)
	{
		if (character >= 'A' && character <= 'z' || character == '*')
		{
			return(true);
		}
		else
		{
			return(false);
		}
	}
	//}}}

	
	
	//{{{check_operand_character
	boolean check_operand_character(int character)
	{
		if (character >= ' ' && character <= 'z')
			{
				return(true);
			}
			else
			{
				return(false);
			}
	}//}}}
	

	//{{{check_label_character
	private boolean check_label_character(int character)
	{
		if (character >= '!' && character <= 'z' && character != ';')
		{
			return(true);
		}
		else
		{
			return(false);
		}
	} //}}}

	//
	////{{{ wipe_line
	private void wipe_line()
	{
		int index;
		for (index = 0;index<132;index++)
		{
			source_line[index]=0;
		}

	} //}}}
	//

	//{{{scan_to_eol
	private boolean scan_to_EOL()
	{
		int index;
		index = 0;
		while(source_line[index] == ' ' || source_line[index] == 9)
		{
			index++;
		}
		if (source_line[index] == 0 || source_line[index] == ';')
		{
			return(true);
		}
		else
		{
			return(false);
		}
	}//}}}

	//{{{log_error
	private void log_error(int error_number)
	{
		error_index++;
		error_table[error_index].line_number = source_line_number;
		error_table[error_index].line_index = line_index;
		error_table[error_index].error_number = error_number;
	
		if (pass2_flag && error_table[error_index-1].line_number != source_line_number)
		{
			no_lc_flag = 1;
			no_obj_flag = 1;
			print_line();
		}
		else if (pass2_flag)
		{
			no_lc_flag = 1;
			no_obj_flag = 1;
			no_line_num_flag = 1;
			no_source_flag = 1;
			print_line();
		}
		else
		{
			no_obj_flag=1;
			print_line();
	
			no_lc_flag = 1;
			no_obj_flag = 1;
			no_line_num_flag = 1;
			no_source_flag = 1;
			print_line();
		}
	}//}}}
	
	//{{{log_label
	boolean log_label()
	{
		if (pass2_flag == false)
		{
			if (scan_for_symbol(hold_label))
			{
				sym_tbl_index++;
				strcpy(symbol_table[sym_tbl_index].label,hold_label);
				symbol_table[sym_tbl_index].address = location_counter;
				strcpy(symbol_table[sym_tbl_index+1].label,"XXX\0");
				return(true);
			}
			else
			{
				log_error(4);
				return(false);
			}
		}
		else
		{
			return(true);
		}
	}//}}}
	
	//
	boolean log_symbol()
	{
		if (pass2_flag == false)
		{
			if (scan_for_symbol(hold_label))
			{
				sym_tbl_index++;
				strcpy(symbol_table[sym_tbl_index].label,hold_label);
				symbol_table[sym_tbl_index].address = temp_converted_number;
				strcpy(symbol_table[sym_tbl_index+1].label,"XXX\0");
				return(true);
			}
			else
			{
				log_error(4);
				return(false);
			}
		}
		else
		{
			return(true);
		}
	}//end method}}}
	

	//{{{print_line
	boolean print_line()
	{
		int local_index = 0;
		
		//int[] lst_line = new int[150];
		//int[] buffer = new int[150];
		//int[] loc_cntr	= new int[10];
		//int[] obj_code = new int[10];
		//int[] line_num = new int[10];
		//int[] source = new int[132];
		//int[] error_line = new int[80];
		
		String lst_line = null;
		String buffer = null;
		String loc_cntr	 = null;
		String obj_code = null;
		String line_num = null;
		String source = null;
		String error_line = null;
		
		local_index = 0;
		
		//lst_line[0] = 0;
		//loc_cntr[0] = 0;
		//obj_code[0] = 0;
		//line_num[0] = 0;
		//source[0] = 0;
		//error_line[0] = 0;
		
		StringBuilder stringBuilder = new StringBuilder();
		java.util.Formatter sprintf = new java.util.Formatter(stringBuilder, java.util.Locale.US);

   // Explicit argument indices may be used to re-order output.
   //formatter.format("%4$2s %3$2s %2$2s %1$2s", "a", "b", "c", "d")
   // -> " d  c  b  a"
	
		if (pass2_flag)
		{
			build_s_records();
		}
	
		if (lst_flag == 1)
		{
			if (no_lc_flag == 0)
			{
				//sprintf(loc_cntr,"\n%.4x ",location_counter);
				sprintf.format("\n%04x ",location_counter);
				loc_cntr = stringBuilder.toString(); //string_to_chars( stringBuilder.toString() );
				stringBuilder.delete(0,stringBuilder.length());
				
			}
			else if (error_table[print_error_index-1].line_number != source_line_number )
			{
				no_lc_flag =0;
				no_obj_flag =1;
				//sprintf(loc_cntr,"\n     ");
				sprintf.format("\n     ");
				loc_cntr = stringBuilder.toString(); //string_to_chars( stringBuilder.toString() );
				stringBuilder.delete(0,stringBuilder.length());
			}
	
			if (no_obj_flag == 0) //Note: A clue to the no object code lines may be here.
			{
				if (hold_obj_1 > -1 && hold_obj_2 == -1 && hold_obj_3 == -1)
				{
					//sprintf(obj_code,"%.2X      ",hold_obj_1);
					sprintf.format("%02X      ",hold_obj_1);
					obj_code = stringBuilder.toString(); //string_to_chars( stringBuilder.toString() );
					stringBuilder.delete(0,stringBuilder.length());
					hold_obj_1=-1;
				}
				else if (hold_obj_1 > -1 && hold_obj_2 > -1 && hold_obj_3 == -1)
				{
					//sprintf(obj_code,"%.2X %.2X   ",hold_obj_1,hold_obj_2);
					sprintf.format("%02X %02X   ",hold_obj_1,hold_obj_2);
					obj_code = stringBuilder.toString(); //string_to_chars( stringBuilder.toString() );
					stringBuilder.delete(0,stringBuilder.length());
					hold_obj_1=-1;
					hold_obj_2=-1;
				}
				else if (hold_obj_1 > -1 && hold_obj_2 > -1 && hold_obj_3 > -1)
				{
					//sprintf(obj_code,"%.2X %.2X %.2X",hold_obj_1,hold_obj_2,hold_obj_3);
					sprintf.format("%02X %02X %02X",hold_obj_1,hold_obj_2,hold_obj_3);
					obj_code = stringBuilder.toString(); //string_to_chars( stringBuilder.toString() );
					stringBuilder.delete(0,stringBuilder.length());
					hold_obj_1=-1;
					hold_obj_2=-1;
					hold_obj_3=-1;
				}
				else
				{
					log_error(31);
					return(false);
				}
				
			}// end if.
			else
			{
				no_obj_flag = 0;
				//sprintf(obj_code,"        ");
				sprintf.format("        ");
				obj_code = stringBuilder.toString(); //string_to_chars( stringBuilder.toString() );
				stringBuilder.delete(0,stringBuilder.length());
			}
	
			if (no_line_num_flag ==1)
			{
				no_line_num_flag = 0;
				//sprintf(line_num,"     ");
				sprintf.format("     ");
				line_num = stringBuilder.toString(); //string_to_chars( stringBuilder.toString() );
				stringBuilder.delete(0,stringBuilder.length());
			}
			else
			{
				//sprintf(line_num,"%.6d |",source_line_number);
				sprintf.format("%06d |",source_line_number);
				line_num = stringBuilder.toString(); //string_to_chars( stringBuilder.toString() );
				stringBuilder.delete(0,stringBuilder.length());
			}
	
	
	
			if (no_source_flag == 1)
			{
				no_source_flag = 0;
				//sprintf(source," ");
				sprintf.format(" ");
				source = stringBuilder.toString(); //string_to_chars( stringBuilder.toString() );
				stringBuilder.delete(0,stringBuilder.length());
			}
			else
			{
				//sprintf(source,"%s",source_line);
				sprintf.format("%s",chars_to_string(source_line));
				source = stringBuilder.toString(); //string_to_chars( stringBuilder.toString() );
				stringBuilder.delete(0,stringBuilder.length());
			}
			//lst_line = strcat(lst_line,loc_cntr);
			//lst_line = strcat(lst_line,obj_code);
			//lst_line = strcat(lst_line,"   \0");
			//lst_line = strcat(lst_line,line_num);
			//lst_line = strcat(lst_line,source);
			lst_line = loc_cntr + obj_code + "   " + line_num + source;
	
			
			//Note: uncomment for debugging.
			System.out.printf("%s",lst_line);

	
	//fputs(lst_line,lst_file_ptr);
	try
	{
	
		lst_file_ptr.write(lst_line); //(chars_to_string(lst_line));
		
		//System.out.println("SSSSSSSSSSSSSSS " +  lst_line); //chars_to_string(lst_line));
	}
	catch(IOException e)
	{                          
		e.printStackTrace();
	}
	
			while (error_table[print_error_index].line_number == source_line_number)
			{
				//sprintf(error_line,"\n*** ERROR in line %d, Error #%d (%s) ***",error_table[print_error_index].line_number,error_table[print_error_index].error_number, error_message[error_table[print_error_index].error_number]);
				sprintf.format("\n*** ERROR in line %d, Error #%d (%s) ***",error_table[print_error_index].line_number,error_table[print_error_index].error_number, error_message[error_table[print_error_index].error_number]);
				error_line = stringBuilder.toString(); // string_to_chars( stringBuilder.toString() );
				stringBuilder.delete(0,stringBuilder.length());
				System.out.printf("%s",error_line);
				//fputs(error_line,lst_file_ptr);
				try
				{
				
					lst_file_ptr.write(error_line); //chars_to_string(error_line));
				}
				catch(IOException e)
				{
					e.printStackTrace();
				}
				
				print_error_index++;
	
			}
		}
		else
		{
			no_source_flag = 0;
			no_lc_flag = 0;
			no_obj_flag = 0;
			no_line_num_flag = 0;
			hold_obj_1=-1;
			hold_obj_2=-1;
			hold_obj_3=-1;
		}
		
		return false; //Note: this line should never be executed.
	}//}}}
	
	
	//
	//
	////}}}
	//
	//
	////{{{ UdbtDwd
	///************************************************************************
	//   UASM65 V1.0 - Understandable Assembler for the 6500 series Microprocessor.
	//   Copyright 1993 by Ted Kosan.
	//
	//************************************************************************/
	//
	//{{{count_words
	void count_words()
	{
	int index,comma_count;
	
		index=0;
		comma_count=0;
		while(hold_operand[index] != 0)
		{
			if (hold_operand[index] == ',')
			{
				comma_count++;
			}
		index++;
		}
		comma_count++;
		location_counter=location_counter + (comma_count*2);
	}//}}}
	
	
	
	
	//{{{process_byte_duplicates
	boolean process_byte_duplicates()
	{
		//int multiplier = 0;
		Number multiplier = new Number();
		//int number = 0;
		Number number = new Number();
		
		//int ascii_multiplier[20],ascii_number[20];
		int[] ascii_multiplier = new int[20];
		int[] ascii_number = new int[20];
		
		int local_index = 0; 
		int count = 0;
		int byte_count = 0;
		
		local_index = 0;
		operand_index = 0;
		byte_count = 0;
	
		while (hold_operand[operand_index] != '(')
		{
			ascii_multiplier[local_index] = hold_operand[operand_index];
			local_index++;
			operand_index++;
			if(operand_index > 100)
			{
				log_error(22);
				return(false);
			}
		}
		ascii_multiplier[local_index] = 0;
	
		if (convert_to_number(ascii_multiplier, multiplier))
		{
			local_index++;
			operand_index++;
			if (hold_operand[operand_index]==39)
			{
				local_index++;
				operand_index++;
				if (hold_operand[operand_index]==39)
				{
					log_error(32);
					return(false);
				}
				number.number = hold_operand[operand_index];
				hold_obj_1=number.number;
				print_line();
				location_counter++;
	
				for (count=1;count< multiplier.number;)
				{
					hold_obj_1 = number.number;
					byte_count++;
					count++;
	
					if (count< multiplier.number)
					{
						hold_obj_2 = number.number;
						byte_count++;
						count++;
					}
	
					if (count< multiplier.number)
					{
						hold_obj_3 = number.number;
						byte_count++;
						count++;
					}
	
	
					no_source_flag=1;
					no_line_num_flag=1;
					print_line();
					location_counter = location_counter + byte_count;
					byte_count = 0;
				}
	
			}
			else if (hold_operand[operand_index] == '?')
			{
				hold_obj_1=0;
				print_line();
				location_counter++;
	
				for (count=1;count< multiplier.number;)
				{
					hold_obj_1 = 0;
					byte_count++;
					count++;
	
					if (count< multiplier.number)
					{
						hold_obj_2 = 0;
						byte_count++;
						count++;
					}
	
					if (count< multiplier.number)
					{
						hold_obj_3 = 0;
						byte_count++;
						count++;
					}
	
	
					no_source_flag=1;
					no_line_num_flag=1;
					print_line();
					location_counter = location_counter + byte_count;
					byte_count = 0;
				}
			}
			else
			{
				local_index=0;
				while (hold_operand[operand_index] != ')')
				{
					ascii_number[local_index] = hold_operand[operand_index];
					local_index++;
					operand_index++;
					if(operand_index > 100)
					{
						log_error(22);
						return(false);
					}
				}
				ascii_number[local_index] = 0;
	
				if (convert_to_number(ascii_number,number))
				{
				hold_obj_1=number.number;
				print_line();
				location_counter++;
	
				for (count=1;count< multiplier.number;)
				{
					hold_obj_1 = number.number;
					byte_count++;
					count++;
	
					if (count< multiplier.number)
					{
						hold_obj_2 = number.number;
						byte_count++;
						count++;
					}
	
					if (count< multiplier.number)
					{
						hold_obj_3 = number.number;
						byte_count++;
						count++;
					}
	
	
					no_source_flag=1;
					no_line_num_flag=1;
					print_line();
					location_counter = location_counter + byte_count;
					byte_count = 0;
				}
	
				}
				else
				{
					log_error(12);
					return(false);
				}
	
			}
			return(true);
		}
		else
		{
			log_error(21);
			return(false);
		}
	}//}}}
	
	
	
	
	
	//{{{process_word_duplicates
	boolean process_word_duplicates()
	{
		//int multiplier = 0;
		Number multiplier = new Number();
		
		//int number;
		Number number = new Number();
		
		int[] ascii_multiplier = new int[20];
		int[] ascii_number = new int[20];
		
		int local_index = 0;
		int count = 0;
		
		
		operand_index = 0;
	
		while (hold_operand[operand_index] != '(')
		{
			ascii_multiplier[local_index] = hold_operand[operand_index];
			local_index++;
			operand_index++;
			if(operand_index > 100)
			{
				log_error(22);
				return(false);
			}
		}
		ascii_multiplier[local_index] = 0;
	
		if (convert_to_number(ascii_multiplier, multiplier))
		{
			local_index++;
			operand_index++;
			if (hold_operand[operand_index]==39)
			{
				log_error(33);
				return(false);
			}
			else if (hold_operand[operand_index] == '?')
			{
				hold_obj_1=0;
				hold_obj_2=0;
				print_line();
	
				location_counter+=2;
	
				for (count=1;count< multiplier.number ;count++)
				{
					no_source_flag=1;
					no_line_num_flag=1;
					hold_obj_1=0;
					hold_obj_2=0;
					print_line();
					location_counter+=2;
				}
			}
			else
			{
				local_index=0;
				while (hold_operand[operand_index] != ')')
				{
					ascii_number[local_index] = hold_operand[operand_index];
					local_index++;
					operand_index++;
					if(operand_index > 100)
					{
						log_error(22);
						return(false);
					}
				}
				ascii_number[local_index] = 0;
	
				if (convert_to_number(ascii_number,number))
				{
				hold_obj_1=number.number & 255;
				hold_obj_2=((number.number >> 8) & 255);
				print_line();
				location_counter+=2;
	
				for (count=1;count< multiplier.number ;count++)
				{
					no_source_flag=1;
					no_line_num_flag=1;
					hold_obj_1=number.number & 255;
					hold_obj_2=((number.number >> 8) & 255);
					print_line();
					location_counter+=2;
				}
	
				}
				else
				{
					log_error(12);
					return(false);
				}
	
			}
			return(true);
		}
		else
		{
			log_error(21);
			return(false);
		}
	}//}}}
	
	
	
	//{{{count_bytes
	void count_bytes()
	{
	int index = 0;
	int comma_count = 0;
	
		index=0;
		comma_count=0;
		while(hold_operand[index] != 0)
		{
			if (hold_operand[index] == ',')
			{
				comma_count++;
			}
			index++;
		}
		comma_count++;
		location_counter=location_counter + comma_count;
	}//}}}
	
	
	
	//{{{process_bytes
	boolean process_bytes()
	{
		//unsigned long int number;
		Number number = new Number();
		int local_index = 0;
		int operand_index = 0;
		int first_num_flag = 0;
		//int ascii_number[20];
		int[] ascii_number = new int[20];
	
		operand_index=0;
		first_num_flag=0;
	
		while(true)
		{
			local_index=0;
	
			while (hold_operand[operand_index] != ',' && hold_operand[operand_index] !=0)
			{
				ascii_number[local_index]=hold_operand[operand_index];
				local_index++;
				operand_index++;
			}
			ascii_number[local_index] = 0;
	
			if (ascii_number[0]=='?')
			{
				number.number = 0;
			}
			else if (convert_to_number(ascii_number,number))
			{
			}
			else
			{
				log_error(12);
				return(false);
			}
	
			if (first_num_flag == 0)
			{
				first_num_flag = 1;
				hold_obj_1=number.number;
				print_line();
				location_counter++;
			}
			else
			{
				no_source_flag=1;
				no_line_num_flag=1;
				hold_obj_1=number.number;
				print_line();
				location_counter++;
			}
	
	
			if (hold_operand[operand_index] != 0)
			{
			operand_index++;
			}
			else
			{
				return(true);
			}
	
		}
	}//}}}
	
	
	
	//process_words.
	boolean process_words()
	{
		//unsigned long int number;
		Number number = new Number();
		
		int local_index = 0;
		int operand_index = 0;
		int first_num_flag = 0;
		
		int[] ascii_number = new int[20];
	
		operand_index=0;
		first_num_flag=0;
	
		while(true)
		{
			local_index=0;
	
			while (hold_operand[operand_index] != ',' && hold_operand[operand_index] !=0)
			{
				ascii_number[local_index]=hold_operand[operand_index];
				local_index++;
				operand_index++;
			}
			ascii_number[local_index] = 0;
	
			if (ascii_number[0]=='?')
			{
				number.number = 0;
			}
			else if (convert_to_number(ascii_number,number))
			{
			}
			else
			{
				log_error(12);
				return(false);
			}
	
			if (first_num_flag == 0)
			{
				first_num_flag = 1;
				hold_obj_1 = number.number & 255;
				hold_obj_2 = ((number.number >> 8) & 255);
				print_line();
				location_counter+=2;
			}
			else
			{
				no_source_flag=1;
				no_line_num_flag=1;
				hold_obj_1 = number.number & 255;
				hold_obj_2 = ((number.number >> 8) & 255);
				print_line();
				location_counter+=2;
			}
	
	
			if (hold_operand[operand_index] != 0)
			{
			operand_index++;
			}
			else
			{
				return(true);
			}
	
		}
	}//}}}
	
	

	//{{{process_ascii_characters
	boolean process_ascii_characters()
	{
		int index = 0;
		int byte_count = 0;
	
		index = 1;
		byte_count = 0;
	
		hold_obj_1=hold_operand[index];
		print_line();
		index++;
		location_counter++;
	
		while(hold_operand[index] != 34)
		{
			if (hold_operand[index] == 0)
			{
				log_error(23);
				return(false);
			}
	
			hold_obj_1 = hold_operand[index];
			index++;
			byte_count++;
	
			if (hold_operand[index] != 34)
			{
				hold_obj_2 = hold_operand[index];
				index++;
				byte_count++;
			}
	
			if (hold_operand[index] != 34)
			{
				hold_obj_3 = hold_operand[index];
				index++;
				byte_count++;
			}
	
			no_source_flag=1;
			no_line_num_flag=1;
			print_line();
			location_counter = location_counter + byte_count;
			byte_count = 0;
	
		}
		return(true);
	}//}}}
	
	
	
	
	//
	//{{{count_ascii_characters
	boolean count_ascii_characters()
	{
		int index;
	
		index = 1;
		if (hold_operand[index] == 34)
		{
			log_error(13);
			return(false);
		}
	
		while(hold_operand[index] != 34)
		{
			if (hold_operand[index] == 0)
			{
				log_error(23);
				return(false);
			}
			index++;
			location_counter++;
		}
		return(true);
	}//}}}
	
	//{{{calculate_duplicates
	boolean calculate_duplicates()
	{
		//unsigned long int multiplier;
		//Integer multiplier = new Integer(0);
		Number multiplier = new Number();
		int[] ascii_multiplier = new int[20];
		int index;
		index = 0;
	
		while (hold_operand[index] != '(')
		{
			ascii_multiplier[index] = hold_operand[index];
			index++;
			if(index > 100)
			{
				log_error(22);
				return(false);
			}
		}
		ascii_multiplier[index] = 0;
		if (convert_to_number(ascii_multiplier, multiplier))
		{
			location_counter=location_counter+ multiplier.number;
			return(true);
		}
		else
		{
			log_error(21);
			return(false);
		}
	}//}}}
	
	//
	//
	////}}}
	//
	//
	////{{{ UFiles
	///************************************************************************
	//   UASM65 V1.0 - Understandable Assembler for the 6500 series Microprocessor.
	//   Copyright 1993 by Ted Kosan.
	//
	//************************************************************************/
	//
	//
	//
	//get_file_name(int *file_name)
	//{
	//	printf("Enter name of source file: ");
	//	scanf("%s",file_name);
	//	if (strstr(file_name,".asm") || strstr(file_name,".ASM\0"))
	//	{
	//		return(true);
	//	}
	//	else
	//	{
	//		return(false);
	//	}
	//}
	//
	//{{{open_file
	java.io.RandomAccessFile open_file(File file)
	{
		java.io.RandomAccessFile file_pointer;
		try 
		{
			file_pointer = new java.io.RandomAccessFile(file,"r");

		}
		catch(Exception ioe)
		{
			ioe.printStackTrace();
			return null;
		}
		return file_pointer;
	

	} //}}}

	
	//{{{open_lst_file
	boolean open_lst_file()
	{
		
		String sourceFilePath = source_file.getPath();
		sourceFilePath = sourceFilePath.substring(0,sourceFilePath.indexOf("."));
		
		lst_filename = sourceFilePath.concat(".lst");
	
		//lst_file_ptr = fopen( lst_filename,"w");
		try 
		{
			lst_file_ptr = new java.io.FileWriter(lst_filename);

		}
		catch(Exception ioe)
		{
			ioe.printStackTrace();
			return false;
		}
		return true;
	
	
	}//}}}

	//{{{read_operator_table                                                                                       
	private boolean read_operator_table()
	{
	op_table[   0] = new op_tbl( "LON\0", "IMP\0", 0x00, 0, 0 );
	op_table[   1] = new op_tbl( "LOF\0", "IMP\0", 0x00, 0, 0 );
	op_table[   2] = new op_tbl( "ORG\0", "DIR\0", 0x00, 0, 0 );
	op_table[   3] = new op_tbl( "*\0", 	"IMP\0", 0x00, 0, 0 );
	op_table[   4] = new op_tbl( "EQU\0", "DIR\0", 0x00, 0, 0 );
	op_table[   5] = new op_tbl( "DBT\0", "DIR\0", 0x00, 0, 0 );
	op_table[   6] = new op_tbl( "DWD\0", "DIR\0", 0x00, 0, 0 );
	op_table[   7] = new op_tbl( "END\0", "IMP\0", 0x00, 0, 0 );
	op_table[   8] = new op_tbl( "ADC\0", "IMM\0", 0x69, 2, 2 );
	op_table[   9] = new op_tbl( "ADC\0", "ABS\0", 0x6D, 3, 4 );
	op_table[  10] = new op_tbl( "ADC\0", "ABX\0", 0x7D, 3, 4 );
	op_table[  11] = new op_tbl( "ADC\0", "ABY\0", 0x79, 3, 4 );
	op_table[  12] = new op_tbl( "ADC\0", "IXR\0", 0x61, 2, 6 );
	op_table[  13] = new op_tbl( "ADC\0", "IRX\0", 0x71, 2, 5 );
	op_table[  14] = new op_tbl( "AND\0", "IMM\0", 0x29, 2, 2 );
	op_table[  15] = new op_tbl( "AND\0", "ABS\0", 0x2D, 3, 4 );
	op_table[  16] = new op_tbl( "AND\0", "ABX\0", 0x3D, 3, 4 );
	op_table[  17] = new op_tbl( "AND\0", "ABY\0", 0x39, 3, 4 );
	op_table[  18] = new op_tbl( "AND\0", "IXR\0", 0x21, 2, 6 );
	op_table[  19] = new op_tbl( "AND\0", "IRX\0", 0x31, 2, 5 );
	op_table[  20] = new op_tbl( "ASL\0", "ACC\0", 0x0A, 1, 2 );
	op_table[  21] = new op_tbl( "ASL\0", "ABS\0", 0x0E, 3, 6 );
	op_table[  22] = new op_tbl( "ASL\0", "ABX\0", 0x1E, 3, 7 );
	op_table[  23] = new op_tbl( "BCC\0", "REL\0", 0x90, 2, 2 );
	op_table[  24] = new op_tbl( "BCS\0", "REL\0", 0xB0, 2, 2 );
	op_table[  25] = new op_tbl( "BEQ\0", "REL\0", 0xF0, 2, 2 );
	op_table[  26] = new op_tbl( "BIT\0", "ABS\0", 0x2C, 3, 4 );
	op_table[  27] = new op_tbl( "BMI\0", "REL\0", 0x30, 2, 2 );
	op_table[  28] = new op_tbl( "BNE\0", "REL\0", 0xD0, 2, 2 );
	op_table[  29] = new op_tbl( "BPL\0", "REL\0", 0x10, 2, 2 );
	op_table[  30] = new op_tbl( "BRK\0", "IMP\0", 0x00, 1, 7 );
	op_table[  31] = new op_tbl( "BVC\0", "REL\0", 0x50, 2, 2 );
	op_table[  32] = new op_tbl( "BVS\0", "REL\0", 0x70, 2, 2 );
	op_table[  33] = new op_tbl( "CLC\0", "IMP\0", 0x18, 1, 2 );
	op_table[  34] = new op_tbl( "CLD\0", "IMP\0", 0xD8, 1, 2 );
	op_table[  35] = new op_tbl( "CLI\0", "IMP\0", 0x58, 1, 2 );
	op_table[  36] = new op_tbl( "CLV\0", "IMP\0", 0xB8, 1, 2 );
	op_table[  37] = new op_tbl( "CMP\0", "IMM\0", 0xC9, 2, 2 );
	op_table[  38] = new op_tbl( "CMP\0", "ABS\0", 0xCD, 3, 4 );
	op_table[  39] = new op_tbl( "CMP\0", "ABX\0", 0xDD, 3, 4 );
	op_table[  40] = new op_tbl( "CMP\0", "ABY\0", 0xD9, 3, 4 );
	op_table[  41] = new op_tbl( "CMP\0", "IXR\0", 0xC1, 2, 6 );
	op_table[  42] = new op_tbl( "CMP\0", "IRX\0", 0xD1, 2, 5 );
	op_table[  43] = new op_tbl( "CPX\0", "IMM\0", 0xE0, 2, 2 );
	op_table[  44] = new op_tbl( "CPX\0", "ABS\0", 0xEC, 3, 4 );
	op_table[  45] = new op_tbl( "CPY\0", "IMM\0", 0xC0, 2, 2 );
	op_table[  46] = new op_tbl( "CPY\0", "ABS\0", 0xCC, 3, 4 );
	op_table[  47] = new op_tbl( "DEC\0", "ABS\0", 0xCE, 3, 6 );
	op_table[  48] = new op_tbl( "DEC\0", "ABX\0", 0xDE, 3, 7 );
	op_table[  49] = new op_tbl( "DEX\0", "IMP\0", 0xCA, 1, 2 );
	op_table[  50] = new op_tbl( "DEY\0", "IMP\0", 0x88, 1, 2 );
	op_table[  51] = new op_tbl( "EOR\0", "IMM\0", 0x49, 2, 2 );
	op_table[  52] = new op_tbl( "EOR\0", "ABS\0", 0x4D, 3, 4 );
	op_table[  53] = new op_tbl( "EOR\0", "ABX\0", 0x5D, 3, 4 );
	op_table[  54] = new op_tbl( "EOR\0", "ABY\0", 0x59, 3, 4 );
	op_table[  55] = new op_tbl( "EOR\0", "IXR\0", 0x41, 2, 6 );
	op_table[  56] = new op_tbl( "EOR\0", "IRX\0", 0x51, 2, 5 );
	op_table[  57] = new op_tbl( "INC\0", "ABS\0", 0xEE, 3, 6 );
	op_table[  58] = new op_tbl( "INC\0", "ABX\0", 0xFE, 3, 7 );
	op_table[  59] = new op_tbl( "INX\0", "IMP\0", 0xE8, 1, 2 );
	op_table[  60] = new op_tbl( "INY\0", "IMP\0", 0xC8, 1, 2 );
	op_table[  61] = new op_tbl( "JMP\0", "ABS\0", 0x4C, 3, 3 );
	op_table[  62] = new op_tbl( "JMP\0", "IND\0", 0x6C, 3, 5 );
	op_table[  63] = new op_tbl( "JSR\0", "ABS\0", 0x20, 3, 6 );
	op_table[  64] = new op_tbl( "LDA\0", "IMM\0", 0xA9, 2, 2 );
	op_table[  65] = new op_tbl( "LDA\0", "ABS\0", 0xAD, 3, 4 );
	op_table[  66] = new op_tbl( "LDA\0", "ABX\0", 0xBD, 3, 4 );
	op_table[  67] = new op_tbl( "LDA\0", "ABY\0", 0xB9, 3, 4 );
	op_table[  68] = new op_tbl( "LDA\0", "IXR\0", 0xA1, 2, 6 );
	op_table[  69] = new op_tbl( "LDA\0", "IRX\0", 0xB1, 2, 5 );
	op_table[  70] = new op_tbl( "LDX\0", "IMM\0", 0xA2, 2, 2 );
	op_table[  71] = new op_tbl( "LDX\0", "ABS\0", 0xAE, 3, 4 );
	op_table[  72] = new op_tbl( "LDX\0", "ABY\0", 0xBE, 3, 4 );
	op_table[  73] = new op_tbl( "LDY\0", "IMM\0", 0xA0, 2, 2 );
	op_table[  74] = new op_tbl( "LDY\0", "ABS\0", 0xAC, 3, 4 );
	op_table[  75] = new op_tbl( "LDY\0", "ABX\0", 0xBC, 3, 4 );
	op_table[  76] = new op_tbl( "LSR\0", "ACC\0", 0x4A, 1, 2 );
	op_table[  77] = new op_tbl( "LSR\0", "ABS\0", 0x4E, 3, 6 );
	op_table[  78] = new op_tbl( "LSR\0", "ABX\0", 0x5E, 3, 7 );
	op_table[  79] = new op_tbl( "NOP\0", "IMP\0", 0xEA, 1, 2 );
	op_table[  80] = new op_tbl( "ORA\0", "IMM\0", 0x09, 2, 2 );
	op_table[  81] = new op_tbl( "ORA\0", "ABS\0", 0x0D, 3, 4 );
	op_table[  82] = new op_tbl( "ORA\0", "ABX\0", 0x1D, 3, 4 );
	op_table[  83] = new op_tbl( "ORA\0", "ABY\0", 0x19, 3, 4 );
	op_table[  84] = new op_tbl( "ORA\0", "IXR\0", 0x01, 2, 6 );
	op_table[  85] = new op_tbl( "ORA\0", "IRX\0", 0x11, 2, 5 );
	op_table[  86] = new op_tbl( "PHA\0", "IMP\0", 0x48, 1, 3 );
	op_table[  87] = new op_tbl( "PHP\0", "IMP\0", 0x08, 1, 3 );
	op_table[  88] = new op_tbl( "PLA\0", "IMP\0", 0x68, 1, 4 );
	op_table[  89] = new op_tbl( "PLP\0", "IMP\0", 0x28, 1, 4 );
	op_table[  90] = new op_tbl( "ROL\0", "ACC\0", 0x2A, 1, 2 );
	op_table[  91] = new op_tbl( "ROL\0", "ABS\0", 0x2E, 3, 6 );
	op_table[  92] = new op_tbl( "ROL\0", "ABX\0", 0x3E, 3, 7 );
	op_table[  93] = new op_tbl( "ROR\0", "ACC\0", 0x6A, 1, 2 );
	op_table[  94] = new op_tbl( "ROR\0", "ABS\0", 0x6E, 3, 6 );
	op_table[  95] = new op_tbl( "ROR\0", "ABX\0", 0x7E, 3, 7 );
	op_table[  96] = new op_tbl( "RTI\0", "IMP\0", 0x40, 1, 6 );
	op_table[  97] = new op_tbl( "RTS\0", "IMP\0", 0x60, 1, 6 );
	op_table[  98] = new op_tbl( "SBC\0", "IMM\0", 0xE9, 2, 2 );
	op_table[  99] = new op_tbl( "SBC\0", "ABS\0", 0xED, 3, 4 );
	op_table[ 100] = new op_tbl( "SBC\0", "ABX\0", 0xFD, 3, 4 );
	op_table[ 101] = new op_tbl( "SBC\0", "ABY\0", 0xF9, 3, 4 );
	op_table[ 102] = new op_tbl( "SBC\0", "IXR\0", 0xE1, 2, 6 );
	op_table[ 103] = new op_tbl( "SBC\0", "IRX\0", 0xF1, 2, 5 );
	op_table[ 104] = new op_tbl( "SEC\0", "IMP\0", 0x38, 1, 2 );
	op_table[ 105] = new op_tbl( "SED\0", "IMP\0", 0xF8, 1, 2 );
	op_table[ 106] = new op_tbl( "SEI\0", "IMP\0", 0x78, 1, 2 );
	op_table[ 107] = new op_tbl( "STA\0", "ABS\0", 0x8D, 3, 4 );
	op_table[ 108] = new op_tbl( "STA\0", "ABX\0", 0x9D, 3, 5 );
	op_table[ 109] = new op_tbl( "STA\0", "ABY\0", 0x99, 3, 5 );
	op_table[ 110] = new op_tbl( "STA\0", "IXR\0", 0x81, 2, 6 );
	op_table[ 111] = new op_tbl( "STA\0", "IRX\0", 0x91, 2, 6 );
	op_table[ 112] = new op_tbl( "STX\0", "ABS\0", 0x8E, 3, 4 );
	op_table[ 113] = new op_tbl( "STY\0", "ABS\0", 0x8C, 3, 4 );
	op_table[ 114] = new op_tbl( "TAX\0", "IMP\0", 0xAA, 1, 2 );
	op_table[ 115] = new op_tbl( "TAY\0", "IMP\0", 0xA8, 1, 2 );
	op_table[ 116] = new op_tbl( "TSX\0", "IMP\0", 0xBA, 1, 2 );
	op_table[ 117] = new op_tbl( "TXA\0", "IMP\0", 0x8A, 1, 2 );
	op_table[ 118] = new op_tbl( "TXS\0", "IMP\0", 0x9A, 1, 2 );
	op_table[ 119] = new op_tbl( "TYA\0", "IMP\0", 0x98, 1, 2 );
	op_table[ 120] = new op_tbl( "XXX\0", "XXX\0", 0x00, 0, 0 );
	
	op_table[ 121] = new op_tbl( "XXX\0", "\0", 0, 0, 0);
	
	return(true);

		/*
		int op_table_file_name[]="op_table.dat";
		FILE *op_table_file_pointer;
		int x;

		x=0;
		if (open_file(op_table_file_name,&op_table_file_pointer))
		{
			while (fscanf(op_table_file_pointer,"%s%s%x%x%x",op_table[x].operator,op_table[x].add_code,&op_table[x].opcode,&op_table[x].num_bytes,&op_table[x].base_cycles)!= EOF)
			{
				x++;
			}	
			strcpy (op_table[x].operator, "XXX\0");
			
			
			/*for (x=0;(strcmp(op_table[x].operator,"end\0")!= 0);x++)
				printf("%s %s %x %x\n",op_table[x].operator,op_table[x].add_code,op_table[x].opcode,op_table[x].num_bytes);
			*/
		/*
			fclose(op_table_file_pointer);
			return(true);
			
	}
		else
		{
			return(false);
	}    
		*/

	}//}}}

	////{{{ build_s_records
	boolean build_s_records()
	{
		/*Note: currently this code just skips dbts with questions marks. the
		reason for this was to make the code romable.
		*/
		if ((hold_operand[operand_index] == '?') || (strcmp(hold_operand,"?\0")==0))
		{
	
			if (s_record[s_record_index].record_length == 3)
			{
				s_record[s_record_index].address = location_counter;
	
				if (strcmp(hold_operator,"DBT\0")==0)
				{
					s_record[s_record_index].address++;
				}
				else
				{
					s_record[s_record_index].address+=2;
				}
			}
			return(true);
		}
	
	
		if (strcmp(hold_operator,"ORG\0")==0)
		{
			s_record_index++;
			code_index=0;
			s_record[s_record_index].record_length = 3;
			s_record[s_record_index].address = location_counter;
	
			/* Note: Temporary - for Understandable 6502 computer only. */
			//if (s_record[s_record_index].address < 0x5000)
			//{
			//	s_record[s_record_index].address = s_record[s_record_index].address+ 0xbc00;
			//}
	
	
			/* if (s_record[s_record_index].address < 0x1000)
			{
			 	s_record[s_record_index].address = 0x1000;
			}
			*/
	
		}
		else
		{
			if (hold_obj_1 > -1)
			{
				s_record[s_record_index].code[code_index] = hold_obj_1;
				code_index++;
				if (code_index >= 20)
				{
					s_record_index++;
					code_index = 0;
					s_record[s_record_index].record_length = 3;
					s_record[s_record_index].address = s_record[s_record_index-1].address + s_record[s_record_index-1].record_length-2;
				}
				else
				{
					s_record[s_record_index].record_length++;
				}
			}
			if (hold_obj_2 > -1)
			{
				s_record[s_record_index].code[code_index] = hold_obj_2;
				code_index++;
				if (code_index >= 20)
				{
					s_record_index++;
					code_index = 0;
					s_record[s_record_index].record_length = 3;
					s_record[s_record_index].address = s_record[s_record_index-1].address + s_record[s_record_index-1].record_length-2;
				}
				else
				{
					s_record[s_record_index].record_length++;
				}
			}
			if (hold_obj_3 > -1)
			{
				s_record[s_record_index].code[code_index] = hold_obj_3;
				code_index++;
				if (code_index >= 20)
				{
					s_record_index++;
					code_index = 0;
					s_record[s_record_index].record_length = 3;
					s_record[s_record_index].address = s_record[s_record_index-1].address + s_record[s_record_index-1].record_length-2;
				}
				else
				{
					s_record[s_record_index].record_length++;
				}
			}
		}
		return false; //Note: this line should never execute.
	}// }}}
	
	
	////{{{convert_src_to_ascii();
	//convert_sr_to_ascii()
	//{
	//	int s_rec_line[80],hold_code[50];
	//	unsigned int sr_index, code_index;
	//	FILE *s_rec_file_ptr;
	//	int s_rec_filename[30];
	//	int local_index;
	//	unsigned int checksum_accumulator, checksum;
	//
	//	local_index = 0;
	//	strcpy (s_rec_filename,source_file_name);
	//
	//	while ( s_rec_filename[local_index] != '.' )
	//	{
	//		local_index++;
	//		if (local_index > 29)
	//		{
	//			printf("\n\nInternal error, problem with S record file name.\n\n");
	//			return(false);
	//		}
	//
	//	}
	//	s_rec_filename[local_index] = 0;
	//	s_rec_filename = strcat(s_rec_filename,".s19\0");
	//
	//	s_rec_file_ptr = fopen( s_rec_filename,"w");
	//
	//	sprintf(s_rec_line,"S007000055415347C8\n");
	//	fputs(s_rec_line,s_rec_file_ptr);
	//
	//	for (sr_index=1; sr_index <= s_record_index; sr_index++)
	//	{
	//		checksum_accumulator = 0;
	//
	//		if (s_record[sr_index].record_length < 22)
	//		{
	//			s_record[sr_index].record_length--;
	//		}
	//		s_record[sr_index].record_length++;
	//
	//		sprintf(s_rec_line,"S1%.2X%.4x",s_record[sr_index].record_length,s_record[sr_index].address);
	//
	//		checksum_accumulator = checksum_accumulator + s_record[sr_index].record_length;
	//		checksum_accumulator = checksum_accumulator + (s_record[sr_index].address & 255);
	//		checksum_accumulator = checksum_accumulator + ((s_record[sr_index].address >> 8) & 255);
	//
	//		for (code_index = 0; code_index < s_record[sr_index].record_length - 3; code_index++)
	//		{
	//			checksum_accumulator = checksum_accumulator + s_record[sr_index].code[code_index];
	//			sprintf(hold_code,"%.2X",s_record[sr_index].code[code_index]);
	//			s_rec_line = strcat(s_rec_line,hold_code);
	//		}
	//		checksum = (~checksum_accumulator) & 255;
	//		sprintf(hold_code,"%.2X",checksum);
	//		s_rec_line = strcat(s_rec_line,hold_code);
	//		s_rec_line = strcat(s_rec_line,"\n\0");
	//		fputs(s_rec_line,s_rec_file_ptr);
	//	}
	//	sprintf(s_rec_line,"S9030000FC\n");
	//	fputs(s_rec_line,s_rec_file_ptr);
	//	fclose(s_rec_file_ptr);
	//} }}}
	//
	//
	//create_sym_file()
	//{
	//	int sym_file_name[30];
	//	FILE *sym_file_ptr;
	//
	//	int sym_line[50];
	//
	//	int local_index;
	//	local_index = 0;
	//	strcpy (sym_file_name,source_file_name);
	//
	//	while ( sym_file_name[local_index] != '.' )
	//	{
	//		local_index++;
	//		if (local_index > 29)
	//		{
	//			printf("\n\nInternal error, problem with sym file name.\n\n");
	//			return(false);
	//		}
	//
	//	}
	//	sym_file_name[local_index] = 0;
	//	sym_file_name = strcat(sym_file_name,".sym\0");
	//
	//	sym_file_ptr = fopen( sym_file_name,"w");
	//
	//
	//	for (x=1;x<=sym_tbl_index;x++)
	//	{
	//		sprintf(sym_line,"%d %s %x \n",x,symbol_table[x].label,symbol_table[x].address);
	//		fputs(sym_line,sym_file_ptr);
	//	}
	//
	//	fclose(sym_file_ptr);
	//
	//}
	//
	////}}}


	//{{{
	public static void main(String[] args)
    {
       int a = 0;
	   System.out.println("AAAAA");
       UASM65 assem = new UASM65();
	   
	   		
















		//bsh.args = new String[] {"one","two"};

		System.out.println("\nUASM65 V1.25 - Understandable Assembler for the 6500 series microprocessors.\nWritten by Ted Kosan.\n");
		



		//If filename was entered from the command line then use it.  If not, then
		// obtain it from the user.


		//	if ( bsh.args != void && bsh.args.length == 2 ) //argc == 2 )
		//	{
		//		source_file_name = bsh.args[1];
		//
		//		if ( ! ( strstr( source_file_name,".asm\0") || strstr(source_file_name,".ASM\0") ) )
		//		{
		//			System.out.println("\n\nBad file name, must have .asm extension.\n\n");
		//			return(false);
		//		}
		//	}
		//	//	else  //Note: Maybe enable this for standalone operation in the future.
		//	{
		//		if ( !get_file_name( source_file_name ) )
		//		{
		//			System.out.println("\n\nBad file name, must have .asm extension.\n\n");
		//			return(false);
		//		}
		//	}//end else

		//
		//
		//
		if (assem.pass1())
		{
			//		create_sym_file();
			//
					if( assem.pass2())
					{
			//			convert_sr_to_ascii();
			//			fclose(source_file_pointer);
			//			fclose(lst_file_ptr);
					}
		}
		//}}}

		assem.closeFiles();
		
    }//}}}



}//end class


// :indentSize=4:lineSeparator=\n:noTabs=false:tabSize=4:folding=custom:collapseFolds=0:



