package org.mathrider.u6502;

import java.io.File;
import java.io.FileReader;
import java.io.IOException;
//import org.gjt.sp.jedit.bsh.EvalError;
import java.util.HashMap;
import java.util.ArrayList;


public class UASM65
{
	private FileReader inputStream;
	
	private String error = "none";


	private int line_character;
	private int[] temp_add_mode = new int [5];
	private String lst_filename = "";// = new int [20];
	private int[] source_line = new int[132];
	private int[] hold_label = new int[50];
	private int[] hold_operator = new int[10];
	private int[] hold_operand = new int[132];
	private int[] hold_add_mode = new int[5];

	private int line_index,sym_tbl_index,x,op_tbl_index,end_flag;
	private int error_index,source_line_number,return_code,symbol_index;
	private int location_counter,hold_location,temp_converted_number;

	private File source_file_pointer, lst_file_ptr;

	private int no_source_flag,no_lc_flag,no_obj_flag,hold_obj_1,hold_obj_2,hold_obj_3;
	private int no_line_num_flag, pass2_flag,print_error_index,lst_flag;
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

		public op_tbl(char[] operator, char[] add_code, int opcode, int num_bytes, int base_cycles)
		{
			for(int x = 0;x < operator.length; x++)
			{
				this.operator[x] = (int) operator[x];
			}

			
			for(int x = 0;x < add_code.length; x++)
			{
				this.add_code[x] = (int) add_code[x];
			}

			opcode = opcode;
			num_bytes = num_bytes;
			base_cycles = base_cycles;
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

	//Note: must decide what to do with these structure arrays.
	//struct op_tbl op_table[150];
	private HashMap op_table = new HashMap();
	//struct sym_tbl symbol_table[600];
	private ArrayList symbol_table = new ArrayList();
	//struct error_tbl error_table[70];
	private ArrayList error_table = new ArrayList();
	//struct s_rec s_record[386];
	private ArrayList s_record = new ArrayList();

        public static void main(String[] args)
        {
            int a = 0;
            //assem = new UASM65();
        }

	public UASM65()
	{
		super();

		try {
			java.io.FileReader inputStream = new java.io.FileReader("c:/ted/checkouts/mathrider/src/examples/experimental/test.asm");

			//            int c;
			//            while ((c = inputStream.read()) != -1) {
			//                System.out.println(""+c);
			//            }
		}
		catch(Exception ioe)
		{
			ioe.printStackTrace();
		}
		finally
		{
			//  if (inputStream != null) {
			//      inputStream.close();
			// }


		}//end try/finally



		//{{{ main










		//bsh.args = new String[] {"one","two"};

		System.out.println("\nUASM65 V1.25 - Understandable Assembler for the 6500 series microprocessors.\nWritten by Ted Kosan.");
		initialize();
		if (!read_operator_table())
		{
			System.out.println("\nCannot find operator table file");
			//return(0);
		}


		//If filename was entered from the command line then use it.  If not, then
		// obtain it from the user.


		//	if ( bsh.args != void && bsh.args.length == 2 ) //argc == 2 )
		//	{
		//		source_file_name = bsh.args[1];
		//
		//		if ( ! ( strstr( source_file_name,".asm") || strstr(source_file_name,".ASM") ) )
		//		{
		//			System.out.println("\n\nBad file name, must have .asm extension.\n\n");
		//			return(0);
		//		}
		//	}
		//	//	else  //Note: Maybe enable this for standalone operation in the future.
		//	{
		//		if ( !get_file_name( source_file_name ) )
		//		{
		//			System.out.println("\n\nBad file name, must have .asm extension.\n\n");
		//			return(0);
		//		}
		//	}//end else

		//
		//
		//
		if (pass1())
		{
			//		create_sym_file();
			//
			//		if( pass2())
			//		{
			//			convert_sr_to_ascii();
			//			fclose(source_file_pointer);
			//			fclose(lst_file_ptr);
			//		}
		}
		//}}}

	}//end constructor



	private int strcmp(int[] first, String second)
	{
		return(0);
	}


	//Note: tempory test file
	//sourceFile = new File("c:/ted/checkouts/mathrider/src/examples/experimental/test.asm");



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
		//error_table[0].line_number = 0; Note: might need to add replacement code here.
	}// end initialize.


	//{{{strcpy
	private void strcpy( int[] destination, int[] source)
	{
		System.out.println(" XXXXXXX " + destination + source);
		System.out.println(" XXXXXXX " + chars_to_string(destination) + chars_to_string(source));
		//returnError = destination;  Note: begin here.
		//throw new EvalError("hello",null,null);
		int index = 0;
		int c;
		do
		{
			c = source[index];
			destination[index] = c;
			index++;
		}while(c != 0);
	}//end method.
	
	private void strcpy( int[] destination, String source)
	{
		byte[] bytes = source.getBytes();
		
		for(int x = 0; x < source.length(); x++)
		{
			destination[x] = bytes[x];
		}
		
	}
	//}}}


	//{{{chars_to_string
	private String chars_to_string(int[] chars)
	{
		int stringEndIndex = 0;
		for(; chars[stringEndIndex] != 0; stringEndIndex++);
		
		return new String(chars,0,stringEndIndex);
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
			return (int)inputStream.read();
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

	private boolean pass1()
	{

		pass2_flag=0;
		lst_flag = 1;
		//	strcpy(symbol_table[1].label,"XXX");

		//	if (open_file(source_file_name))// ,&source_file_pointer))
		//	{
		//		open_lst_file();
		//
//		scan_lines(1);
		//
		//
		//		if (end_flag == 0)
		//		{
		//			log_error(3);
		//		}
		//
		//
		///*
		//		for (x=1;x<=sym_tbl_index;x++)
		//		{
		//			printf("\n%d %s %lx",x,symbol_table[x].label,symbol_table[x].address);
		//
		//		}
		//*/
		//
		//
		//
		//		if (error_index == 0 && end_flag == 1)
		//		{
		//			printf("\n\nPass1 0 errors\n");
		//			return(1);
		//		}
		//		else
		//		{
		//			printf("\n\nPass1 %d errors.\n",error_index);
		//
		//			/*
		//			for (x=1;x<=error_index;x++)
		//			{
		//				printf("\nline#: %d    ",error_table[x].line_number);
		//				printf("error number: %d",error_table[x].error_number);
		//			}
		//			*/
		//
		//			printf("\n");
		//			fclose(source_file_pointer);
		//			fclose(lst_file_ptr);
		//			return(0);
		//		}
		//
		//	}
		//	else
		//	{
		//		printf("\nFile not found");
		//		return(0);
		//	}
		//
	return false; }//}}}End pass1.
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
	//pass2()
	//{
	//	pass2_flag=1;
	//	fseek(source_file_pointer,0,SEEK_SET);
	//	initialize();
	//	scan_lines(2);
	//
	//	if (error_index == 0 && end_flag == 1)
	//	{
	//		printf("\nPass2 0 errors\n\n");
	//		return(1);
	//	}
	//	else
	//	{
	//		printf("\nPass2 %d errors.\n\n",error_index);
	///*
	//		for (x=1;x<=error_index;x++)
	//		{
	//			printf("\nline#: %d    ",error_table[x].line_number);
	//			printf("line index: %d    ",error_table[x].line_index);
	//			printf("error number: %d",error_table[x].error_number);
	//		}
	//*/
	//		printf("\n");
	//		fclose(source_file_pointer);
	//
	//		return(0);
	//	}
	//
	//
	//}
	//

/*
	//{{{scan_lines
	private void scan_lines(int pass_flag)
	{
		while ((line_character=fgetc()) != EOF)
		{

			if (line_character == 13)
			{
				if (fgetc() == 10)
				{
					source_line[line_index]=0;
					parse_line();
					line_index = 0;
					wipe_line();

				}
				else
				{
					fseek(source_file_pointer,-1,SEEK_CUR);
					source_line[line_index]=0;
					parse_line();
					line_index = 0;
					wipe_line();

				}
			}
			else if (line_character == 10)
			{
				source_line[line_index]=0;
				return_code=parse_line();
				if (return_code != 0 && pass_flag == 1)
				{
					interpret_line_pass1();
				}
				else if (return_code != 0 && pass_flag ==2)
				{
					interpret_line_pass2();
				}
				else if (return_code == 0 && pass_flag == 2)
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
*/

	//{{{ parse_line
	private boolean parse_line()
	{
		int error_number;
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


		if (strcmp(temp_add_mode,"IMP")!=0)
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
			strcpy(hold_operand,"       ");
			strcpy(hold_add_mode,"IMP");
		}

		/*
			if (! pass2_flag)
			{
				printf("%d  %lx : %s : %s : %s : %s\n",source_line_number,location_counter, hold_label, hold_operator,hold_operand,hold_add_mode);
			}
		*/

		return(true);
	}//}}}



	//
	//{{ scan_label
	private int scan_label()
	{
	//	while (source_line[line_index] != ' ' && source_line[line_index] != 9)
	//	{
	//		if (check_label_character(source_line[line_index]))
	//		{
	//			hold_label[line_index] = source_line[line_index];
	//		}
	//		else
	//		{
	//			return(1);
	//		}
	//
	//		line_index++;
	//		if (line_index > 50)
	//		{
	//			return(18);
	//		}
	//	}
	//	hold_label[line_index] = 0;
	//	hold_location = location_counter;
	//	str_to_upper(hold_label);
	//	return(0);
	return 0; }//}}}
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
		//	op_tbl_index = 0;
		//	while (true)
		//	{
		//		if ((strcmp(op_table[op_tbl_index].operator,"XXX"))== 0)
		//		{
		//			return(false);
		//		}
		//		else if ((strcmp(op_table[op_tbl_index].operator,hold_operator))== 0)
		//		{
		//			strcpy(temp_add_mode,op_table[op_tbl_index].add_code);
		//			return(true);
		//		}
		//		op_tbl_index++;
		//	}
		//
		String operator = chars_to_string(hold_operator);
		if(op_table.containsKey(operator))
		{
			//System.out.println(" XXXXX " + chars_to_string(op_table.get(operator).add_code) );return;
			strcpy(temp_add_mode,((op_tbl)op_table.get(operator)).add_code);
			return(true);
		}
		else
		{
			return(false);
		}
		//return(0);
	}//}}}

	//
	//{{{scan_operand
	private int scan_operand()
	{
	//	int local_line_index;
	//	local_line_index = 0;
	//	if (source_line[line_index] == 34)
	//	{
	//		hold_operand[0]= 34;
	//		local_line_index++;
	//		line_index++;
	//		while (source_line[line_index] != 34)
	//		{
	//			if (check_operand_character(source_line[line_index]))
	//			{
	//				hold_operand[local_line_index] = source_line[line_index];
	//			}
	//			else
	//			{
	//				return(21);
	//			}
	//
	//			line_index++;
	//			local_line_index++;
	//			if (local_line_index > 90)
	//			{
	//				return(22);
	//			}
	//		}
	//		hold_operand[local_line_index] = 34;
	//		hold_operand[local_line_index+1] = 0;
	//		return(0);
	//	}
	//	else
	//	{
	//		while (source_line[line_index] != ' '&& source_line[line_index] != 9 && source_line[line_index] != ';'&& source_line[line_index] != 0)
	//		{
	//			if (check_operand_character(source_line[line_index]))
	//			{
	//				hold_operand[local_line_index] = source_line[line_index];
	//			}
	//			else
	//			{
	//				return(21);
	//			}
	//
	//			line_index++;
	//			local_line_index++;
	//			if (local_line_index > 90)
	//			{
	//				return(22);
	//			}
	//		}
	//		hold_operand[local_line_index] = 0;
	//		if (hold_operand[1] != 39)
	//		{
	//			str_to_upper(hold_operand);
	//		}
	//		parse_operand();
	//		return(0);
	//	}
	//
	return 0; }//end method }}}
	//
	//
	//parse_operand()
	//{
	//int local_index;
	//local_index = 0;
	//	if (hold_operand[0] == '#')
	//	{
	//		strcpy(hold_add_mode,"IMM");
	//	}
	//	else if ((hold_operand[0] == 'A' || hold_operand[0] == 'a') && hold_operand[1] == 0)
	//	{
	//		strcpy(hold_add_mode,"ACC");
	//	}
	//	else if (hold_operand[0] == '(')
	//	{
	//	 	local_index++;
	//	 	while (hold_operand[local_index] != 0)
	//	 	{
	//	 		if (hold_operand[local_index] == ',')
	//	 		{
	//	 			if (hold_operand[local_index+1] == 'x' || hold_operand[local_index+1] == 'X')
	//	 			{
	//	 				strcpy(hold_add_mode,"IXR");
	//	 				return(1);
	//	 			}
	//	 			else
	//	 			{
	//	 				log_error(16);
	//	 				return(0);
	//	 			}
	//	 		}
	//	 		else if (hold_operand[local_index] == ')')
	//	 		{
	//	 			local_index++;
	//	 			while (hold_operand[local_index] != 0)
	//	 			{
	//	 				if (hold_operand[local_index] == ',')
	//	 				{
	//	 					if (hold_operand[local_index+1] == 'y' || hold_operand[local_index+1] == 'Y')
	//	 					{
	//	 						strcpy(hold_add_mode,"IRX");
	//	 						return(1);
	//	 					}
	//	 					else
	//	 					{
	//	 						log_error(16);
	//	 						return(0);
	//	 					}
	//	 				}
	//	 				else
	//	 				{
	//	 					log_error(16);
	//	 					return(0);
	//	 				}
	//	 				local_index++;
	//	 			}
	//	 			strcpy(hold_add_mode,"IND");
	//	 			return(1);
	//	 		}
	//	 		local_index++;
	//	 	}
	//		log_error(16);
	//		return(0);
	//	}
	//	else if (strcmp(hold_operand,"")==0)
	//	{
	//		log_error(6);
	//		return(0);
	//	}
	//	else
	//	{
	//	 	while (hold_operand[local_index] != 0)
	//	 	{
	//	 		if (hold_operand[local_index] == ',')
	//	 		{
	//	 			if (hold_operand[local_index+1] == 'x' || hold_operand[local_index+1] == 'X')
	//	 			{
	//	 				strcpy(hold_add_mode,"ABX");
	//	 				return(1);
	//	 			}
	//	 			else if (hold_operand[local_index+1] == 'y' || hold_operand[local_index+1] == 'Y')
	//	 			{
	//	 				strcpy(hold_add_mode,"ABY");
	//	 				return(1);
	//	 			}
	//	 		}
	//	 		local_index++;
	//	 	}
	//	 	if (strcmp(op_table[op_tbl_index].add_code,"REL")==0)
	//	 	{
	//	 		strcpy(hold_add_mode,"REL");
	//	 		return(1);
	//	 	}
	//	 	else
	//	 	{
	//	 		strcpy(hold_add_mode,"ABS");
	//	 	}
	//	}
	//	return(1);
	//}
	//
	//
	//interpret_line_pass1()
	//{
	//
	//	if (hold_label[0] != 0 && (strcmp(hold_operator,"EQU")!=0))
	//	{
	//		log_label();
	//	}
	//
	//
	//	if (strcmp(hold_operator,"LON")==0)
	//	{
	//		lst_flag=1;
	//	}
	//	else if (strcmp(hold_operator,"LOF")==0)
	//	{
	//		lst_flag=0;
	//	}
	//	else if (strcmp(hold_operator,"ORG")==0)
	//	{
	//		if (convert_to_number(hold_operand,&temp_converted_number))
	//		{
	//			if (temp_converted_number >=0 && temp_converted_number <=65535)
	//			{
	//				location_counter=temp_converted_number;
	//			}
	//			else
	//			{
	//				log_error(8);
	//				return(0);
	//			}
	//		}
	//		else
	//		{
	//			log_error(16);
	//			return(0);
	//		}
	//	}
	//	else if (strcmp(hold_operator,"END")==0)
	//	{
	//		end_flag=1;
	//	}
	//	else if (strcmp(hold_operator,"*")==0)
	//	{
	//	;
	//	}
	//	else if (strcmp(hold_operator,"EQU")==0)
	//	{
	//		if (convert_to_number(hold_operand,&temp_converted_number))
	//		{
	//			log_symbol();
	//		}
	//		else
	//		{
	//			log_error(16);
	//			return(0);
	//		}
	//	}
	//	else if (strcmp(hold_operator,"DBT")==0)
	//	{
	//		if (hold_operand[0] == 34) /* Check for ASCII by finding " */
	//		{
	//			count_ascii_characters();
	//		}
	//		else if (strrchr(hold_operand,'(') && strrchr(hold_operand,'('))
	//		{
	//			calculate_duplicates();
	//		}
	//		else if (strrchr(hold_operand,','))
	//		{
	//			count_bytes();
	//		}
	//		else if (strcmp(hold_operand,"?")==0)
	//		{
	//			location_counter++;
	//		}
	//		else if ((strchr(hold_operand,'D') || strchr(hold_operand,'B') || strchr(hold_operand,'H')) && hold_operand[0] != '#')
	//		{
	//			location_counter++;
	//		}
	//		else if (hold_operand[0] == '#')
	//		{
	//			location_counter++;
	//		}
	//		else
	//		{
	//			log_error(16);
	//			return(0);
	//		}
	//	}
	//	else if (strcmp(hold_operator,"DWD")==0)
	//	{
	//		if (strrchr(hold_operand,','))
	//		{
	//			count_words();
	//		}
	//		else if (strcmp(hold_operand,"?")==0)
	//		{
	//			location_counter++;
	//			location_counter++;
	//		}
	//
	//	}
	//	else
	//	{
	//		valid_mnemonic_scan();
	//		while (strcmp(hold_operator,op_table[op_tbl_index].operator)==0)
	//		{
	//			if(strcmp(hold_add_mode,op_table[op_tbl_index].add_code)==0)
	//			{
	//				location_counter=location_counter + op_table[op_tbl_index].num_bytes;
	//				return(1);
	//			}
	//			op_tbl_index++;
	//		}
	//		log_error(30);
	//		return(0);
	//	}
	//
	//}
	//
	//
	////}}}


	//
	//interpret_line_pass2()
	//{
	//	int operand_index,local_index;
	//	char ascii_hold[50]; /* Note: was 10. */
	//	unsigned long int number;
	//
	//	if (strcmp(hold_operator,"LON")==0)
	//	{
	//		no_obj_flag = 1;
	//		lst_flag=1;
	//		print_line();
	//	}
	//	else if (strcmp(hold_operator,"LOF")==0)
	//	{
	//		no_obj_flag = 1;
	//		print_line();
	//		lst_flag=0;
	//	}
	//	else if (strcmp(hold_operator,"ORG")==0)
	//	{
	//		if (convert_to_number(hold_operand,&temp_converted_number))
	//		{
	//			if (temp_converted_number >=0 && temp_converted_number <=65535)
	//			{
	//				location_counter=temp_converted_number;
	//				no_obj_flag = 1;
	//				print_line();
	//				return(1);
	//			}
	//			else
	//			{
	//				log_error(8);
	//				return(0);
	//			}
	//		}
	//		else
	//		{
	//			log_error(16);
	//			return(0);
	//		}
	//	}
	//	else if (strcmp(hold_operator,"END")==0)
	//	{
	//		no_lc_flag=1;
	//		print_line();
	//		end_flag=1;
	//	}
	//	else if (strcmp(hold_operator,"*")==0)
	//	{
	//		no_obj_flag = 1;
	//		print_line();
	//	}
	//	else if (strcmp(hold_operator,"EQU")==0)
	//	{
	//		no_obj_flag = 1;
	//		print_line();
	//	}
	//	else if (strcmp(hold_operator,"DBT")==0)
	//	{
	//		if (hold_operand[0] == 34) /* Check for ASCII by finding " */
	//		{
	//			process_ascii_characters();
	//		}
	//		else if (strrchr(hold_operand,'(') && strrchr(hold_operand,'('))
	//		{
	//			process_byte_duplicates();
	//		}
	//		else if (strrchr(hold_operand,','))
	//		{
	//			process_bytes();
	//		}
	//		else if (strcmp(hold_operand,"?")==0)
	//		{
	//			hold_obj_1 = 0;
	//			print_line();
	//			location_counter++;
	//		}
	//		else if ((strchr(hold_operand,'D') || strchr(hold_operand,'B') || strchr(hold_operand,'H')) && hold_operand[0] != '#')
	//		{
	//			local_index = 0;
	//			operand_index = 0;
	//
	//			while (hold_operand[operand_index] != 0)
	//			{
	//				ascii_hold[local_index] = hold_operand[operand_index];
	//				local_index++;
	//				operand_index++;
	//				if(operand_index > 100)
	//				{
	//					log_error(22);
	//					return(0);
	//				}
	//			}
	//			ascii_hold[local_index] = 0;
	//
	//			if ( is_number(ascii_hold) )
	//			{
	//				if (convert_to_number(ascii_hold,&number))
	//				{
	//					if (number >= 0 && number <= 255)
	//					{
	//						hold_obj_1 = number & 255;
	//						print_line();
	//						location_counter++;
	//						return(1);
	//					}
	//					else
	//					{
	//						log_error(8);
	//						return(0);
	//					}
	//				}
	//				else
	//				{
	//					log_error(16);
	//					return(0);
	//				}
	//			}
	//
	//		}
	//
	//
	//		else if (hold_operand[0] == '#')
	//		{
	//		local_index = 0;
	//		operand_index = 0;
	//
	//		operand_index++;
	//
	//		while (hold_operand[operand_index] != 0 && hold_operand[operand_index] != '<' && hold_operand[operand_index] != '>')
	//		{
	//			ascii_hold[local_index] = hold_operand[operand_index];
	//			local_index++;
	//			operand_index++;
	//			if(operand_index > 100)
	//			{
	//				log_error(22);
	//				return(0);
	//			}
	//		}
	//		ascii_hold[local_index] = 0;
	//
	//		if ( is_number(ascii_hold) )
	//		{
	//			if (convert_to_number(ascii_hold,&number))
	//			{
	//				if (number >= 0 && number <=255)
	//				{
	//					hold_obj_1 = number & 255;
	//					return(1);
	//				}
	//				else
	//				{
	//					log_error(8);
	//					return(0);
	//				}
	//			}
	//			else
	//			{
	//				log_error(16);
	//				return(0);
	//			}
	//		}
	//		else
	//		{
	//			if (! scan_for_symbol(ascii_hold))
	//			{
	//				if (hold_operand[operand_index] == '<')
	//				{
	//					hold_obj_1 = (symbol_table[symbol_index].address & 255);
	//					print_line();
	//					location_counter++;
	//					return(1);
	//				}
	//				else if (hold_operand[operand_index] == '>')
	//				{
	//					hold_obj_1 = ((symbol_table[symbol_index].address >> 8) & 255);
	//					print_line();
	//					location_counter++;
	//					return(1);
	//				}
	//				else
	//				{
	//					hold_obj_1 = (symbol_table[symbol_index].address & 255);
	//					print_line();
	//					location_counter++;
	//					return(1);
	//				}
	//			}
	//			else
	//			{
	//				log_error(2);
	//				return(0);
	//			}
	//		}
	//
	//
	//		}
	//		else
	//		{
	//			log_error(16);
	//			return(0);
	//		}
	//
	//	}
	//	else if (strcmp(hold_operator,"DWD")==0)
	//	{
	//		if (hold_operand[0] == 34) /* Check for ASCII by finding " */
	//		{
	//			log_error(33);
	//			return(0);
	//		}
	//		else if (strrchr(hold_operand,'(') && strrchr(hold_operand,'('))
	//		{
	//			process_word_duplicates();
	//		}
	//		else if (strrchr(hold_operand,','))
	//		{
	//			process_words();
	//		}
	//		else if (strcmp(hold_operand,"?")==0)
	//		{
	//			hold_obj_1 = 0;
	//			hold_obj_2 = 0;
	//			print_line();
	//
	//			location_counter+=2;
	//		}
	//		else
	//		{
	//			log_error(6);
	//			return(0);
	//		}
	//
	//	}
	//	else
	//	{
	//		valid_mnemonic_scan();
	//		while (strcmp(hold_operator,op_table[op_tbl_index].operator)==0)
	//		{
	//			if(strcmp(hold_add_mode,op_table[op_tbl_index].add_code)==0)
	//			{
	//				hold_obj_1 = op_table[op_tbl_index].opcode;
	//				if (parse_operand_pass2())
	//				{
	//					print_line();
	//					location_counter=location_counter + op_table[op_tbl_index].num_bytes;
	//					return(1);
	//				}
	//				else
	//				{
	//					log_error(16);
	//					return(0);
	//				}
	//
	//			}
	//			op_tbl_index++;
	//		}
	//		log_error(16);
	//		return(0);
	//	}
	//
	//}
	//
	//parse_operand_pass2()
	//{
	//	char hold_add_code[5],ascii_hold[120];
	//	int local_index,operand_index;
	//	unsigned long int next_inst_add;
	//	unsigned long int number, temp_number;
	//	long int offset;
	//	local_index = 0;
	//	operand_index = 0;
	//
	//
	//	strcpy (hold_add_code,op_table[op_tbl_index].add_code);
	//
	//	if (strcmp(hold_add_code,"IMP")==0)
	//	{
	//		return(1);
	//	}
	//	if (strcmp(hold_add_code,"ACC")==0)
	//	{
	//		if (strcmp(hold_operand,"A") == 0 || strcmp(hold_operand,"a") == 0)
	//			{
	//				return(1);
	//			}
	//			else
	//			{
	//				log_error(16);
	//				return(0);
	//			}
	//	}
	//	else if (strcmp(hold_add_code,"IMM") == 0)
	//	{
	//		operand_index++;
	//
	//		if (hold_operand[operand_index] == 39)
	//		{
	//			operand_index++;
	//			if (check_operand_character(hold_operand[operand_index]) && hold_operand[operand_index+1] == 39)
	//			{
	//				hold_obj_2 = hold_operand[operand_index];
	//				return(1);
	//
	//			}
	//			else
	//			{
	//				log_error(16);
	//				return(0);
	//			}
	//
	//		}
	//
	//		while (hold_operand[operand_index] != 0 && hold_operand[operand_index] != '<' && hold_operand[operand_index] != '>')
	//		{
	//			ascii_hold[local_index] = hold_operand[operand_index];
	//			local_index++;
	//			operand_index++;
	//			if(operand_index > 100)
	//			{
	//				log_error(22);
	//				return(0);
	//			}
	//		}
	//		ascii_hold[local_index] = 0;
	//
	//		if ( is_number(ascii_hold) )
	//		{
	//			if (convert_to_number(ascii_hold,&number))
	//			{
	//				if (number >= 0 && number <=255)
	//				{
	//					hold_obj_2 = number & 255;
	//					return(1);
	//				}
	//				else
	//				{
	//					log_error(8);
	//					return(0);
	//				}
	//			}
	//			else
	//			{
	//				log_error(16);
	//				return(0);
	//			}
	//		}
	//		else
	//		{
	//			if (! scan_for_symbol(ascii_hold))
	//			{
	//				if (hold_operand[operand_index] == '<')
	//				{
	//					hold_obj_2 = (symbol_table[symbol_index].address & 255);
	//					return(1);
	//				}
	//				else if (hold_operand[operand_index] == '>')
	//				{
	//					hold_obj_2 = ((symbol_table[symbol_index].address >> 8) & 255);
	//					return(1);
	//				}
	//				else
	//				{
	//					hold_obj_2 = (symbol_table[symbol_index].address & 255);
	//					return(1);
	//				}
	//			}
	//			else
	//			{
	//				log_error(2);
	//				return(0);
	//			}
	//		}
	//	}
	//	else if (strcmp(hold_add_code,"ABS") == 0)
	//	{
	//		while (hold_operand[operand_index] != 0 && hold_operand[operand_index] != '+')
	//		{
	//			ascii_hold[local_index] = hold_operand[operand_index];
	//			local_index++;
	//			operand_index++;
	//			if(operand_index > 100)
	//			{
	//				log_error(22);
	//				return(0);
	//			}
	//		}
	//		ascii_hold[local_index] = 0;
	//
	//		if ( is_number(ascii_hold) )
	//		{
	//			if (convert_to_number(ascii_hold,&number))
	//			{
	//				if (number >= 0 && number <= 65535)
	//				{
	//					hold_obj_2 = number & 255;
	//					hold_obj_3 = ((number >> 8) & 255);
	//					return(1);
	//				}
	//				else
	//				{
	//					log_error(8);
	//					return(0);
	//				}
	//			}
	//			else
	//			{
	//				log_error(16);
	//				return(0);
	//			}
	//		}
	//		else
	//		{
	//			if (! scan_for_symbol(ascii_hold))
	//			{
	//				if (hold_operand[operand_index] == '+')
	//				{
	//					local_index = 0;
	//					operand_index++;
	//					while (hold_operand[operand_index] != 0)
	//					{
	//						ascii_hold[local_index] = hold_operand[operand_index];
	//						local_index++;
	//						operand_index++;
	//						if(operand_index > 100) /* Be aware of overrun error */
	//						{
	//							log_error(22);
	//							return(0);
	//						}
	//					}
	//					ascii_hold[local_index] = 0;
	//					if ( is_number(ascii_hold) )
	//					{
	//						if (convert_to_number(ascii_hold,&number))
	//						{
	//							if (number >= 0 && number <= 65535)
	//							{
	//								temp_number = symbol_table[symbol_index].address + number;
	//								hold_obj_2 = temp_number & 255;
	//								hold_obj_3 = ((temp_number >> 8) & 255);
	//								return(1);
	//							}
	//							else
	//							{
	//								log_error(8);
	//								return(0);
	//							}
	//						}
	//						else
	//						{
	//							log_error(16);
	//							return(0);
	//						}
	//					}
	//
	//				}
	//				else
	//				{
	//					hold_obj_2 = symbol_table[symbol_index].address & 255;
	//					hold_obj_3 = ((symbol_table[symbol_index].address >> 8) & 255);
	//					return(1);
	//				}
	//			}
	//			else
	//			{
	//				log_error(2);
	//				return(0);
	//			}
	//		}
	//
	//	}
	//	else if (strcmp(hold_add_code,"ABX") == 0 || strcmp(hold_add_code,"ABY") == 0)
	//	{
	//		while (hold_operand[operand_index] != 0 && hold_operand[operand_index] != ',')
	//		{
	//			ascii_hold[local_index] = hold_operand[operand_index];
	//			local_index++;
	//			operand_index++;
	//			if(operand_index > 100)
	//			{
	//				log_error(22);
	//				return(0);
	//			}
	//		}
	//		ascii_hold[local_index] = 0;
	//
	//		if ( is_number(ascii_hold) )
	//		{
	//			if (convert_to_number(ascii_hold,&number))
	//			{
	//				if (number >= 0 && number <= 65535)
	//				{
	//					hold_obj_2 = number & 255;
	//					hold_obj_3 = ((number >> 8) & 255);
	//					return(1);
	//				}
	//				else
	//				{
	//					log_error(8);
	//					return(0);
	//				}
	//			}
	//			else
	//			{
	//				log_error(16);
	//				return(0);
	//			}
	//		}
	//		else
	//		{
	//			if (! scan_for_symbol(ascii_hold))
	//			{
	//				hold_obj_2 = symbol_table[symbol_index].address & 255;
	//				hold_obj_3 = ((symbol_table[symbol_index].address >> 8) & 255);
	//				return(1);
	//			}
	//			else
	//			{
	//				log_error(2);
	//				return(0);
	//			}
	//		}
	//
	//	}
	//	else if (strcmp(hold_add_code,"IRX") == 0 || strcmp(hold_add_code,"IXR") == 0 || strcmp(hold_add_code,"IND") == 0)
	//	{
	//		operand_index++;
	//		while (hold_operand[operand_index] != ',' && hold_operand[operand_index] != ')')
	//		{
	//			ascii_hold[local_index] = hold_operand[operand_index];
	//			local_index++;
	//			operand_index++;
	//			if(operand_index > 100)
	//			{
	//				log_error(22);
	//				return(0);
	//			}
	//		}
	//		ascii_hold[local_index] = 0;
	//
	//		if ( is_number(ascii_hold) )
	//		{
	//			if (convert_to_number(ascii_hold,&number))
	//			{
	//				if (strcmp(hold_add_code,"IND") == 0)
	//				{
	//					if (number >= 0 && number <= 65535)
	//					{
	//						hold_obj_2 = number & 255;
	//						hold_obj_3 = ((number >> 8) & 255);
	//						return(1);
	//					}
	//					else
	//					{
	//						log_error(8);
	//						return(0);
	//					}
	//				}
	//				else
	//				{
	//					if (number >= 0 && number <= 255)
	//					{
	//						hold_obj_2 = number & 255;
	//						return(1);
	//					}
	//					else
	//					{
	//						log_error(8);
	//						return(0);
	//					}
	//				}
	//			}
	//			else
	//			{
	//				log_error(16);
	//				return(0);
	//			}
	//		}
	//		else
	//		{
	//			if (! scan_for_symbol(ascii_hold))
	//			{
	//				if (strcmp(hold_add_code,"IND") == 0)
	//				{
	//					if (symbol_table[symbol_index].address >= 0 && symbol_table[symbol_index].address <= 65535)
	//					{
	//						hold_obj_2 = symbol_table[symbol_index].address & 255;
	//						hold_obj_3 = ((symbol_table[symbol_index].address >> 8) & 255);
	//						return(1);
	//					}
	//					else
	//					{
	//						log_error(8);
	//						return(0);
	//					}
	//				}
	//				else
	//				{
	//					if (symbol_table[symbol_index].address >= 0 && symbol_table[symbol_index].address <= 255)
	//					{
	//						hold_obj_2 = symbol_table[symbol_index].address & 255;
	//						return(1);
	//					}
	//					else
	//					{
	//						log_error(8);
	//						return(0);
	//					}
	//				}
	//			}
	//			else
	//			{
	//				log_error(2);
	//				return(0);
	//			}
	//		}
	//
	//	}
	//	else if (strcmp(hold_add_code,"REL") == 0)
	//	{
	//		next_inst_add = location_counter;
	//		next_inst_add = next_inst_add + op_table[op_tbl_index].num_bytes;
	//		while (hold_operand[operand_index] != 0)
	//		{
	//			ascii_hold[local_index] = hold_operand[operand_index];
	//			local_index++;
	//			operand_index++;
	//			if(operand_index > 100)
	//			{
	//				log_error(22);
	//				return(0);
	//			}
	//		}
	//		ascii_hold[local_index] = 0;
	//
	//		if ( is_number(ascii_hold) )
	//		{
	//				log_error(34);
	//				return(0);
	//		}
	//		else
	//		{
	//			if (! scan_for_symbol(ascii_hold))
	//			{
	//				offset = symbol_table[symbol_index].address - next_inst_add;
	//				if (offset <= 127 && offset >= -128)
	//				{
	//					hold_obj_2 = offset & 255;
	//					return(1);
	//				}
	//				else
	//				{
	//					log_error(9);
	//					return(0);
	//				}
	//			}
	//			else
	//			{
	//				log_error(2);
	//				return(0);
	//			}
	//		}
	//
	//	}
	//	else
	//	{
	//		log_error(16);
	//		return(0);
	//	}
	//
	//}
	//
	//
	//scan_for_symbol(char symbol[])
	//{
	//	symbol_index = 0;
	//	while(1)
	//	{
	//		if (strcmp(symbol_table[symbol_index].label,symbol)==0)
	//		{
	//			return(0);
	//		}
	//		else if (strcmp(symbol_table[symbol_index].label,"XXX")==0)
	//		{
	//			return(1);
	//		}
	//		symbol_index++;
	//	}
	//}
	//// }}}
	//
	//
	////{{{ UUtility
	///************************************************************************
	//   UASM65 V1.0 - Understandable Assembler for the 6500 series Microprocessor.
	//   Copyright 1993 by Ted Kosan.
	//
	//************************************************************************/
	//
	//
	//convert_to_number(int string_form[20],unsigned long int *number_form)
	//{
	//	int number_base;
	//	int number_base_position,return_code;
	//	long int result;
	//
	//	number_base_position = strlen(string_form)-1;
	//	number_base = string_form[number_base_position];
	//	string_form[number_base_position] = 0;
	//
	//	if (number_base == 'B' || number_base == 'b')
	//	{
	//		return_code = bin_to_integer(string_form,number_form);
	//	}
	//	else if (number_base == 'D' || number_base == 'd')
	//	{
	//		return_code = dec_to_integer(string_form,number_form);
	//	}
	//	else if (number_base == 'H' || number_base == 'h')
	//	{
	//		return_code = hex_to_integer(string_form,number_form);
	//	}
	//	else
	//	{
	//		log_error(24);
	//		return_code = 0;
	//	}
	//
	//	*number_form = *number_form & 65535;
	//	return (return_code);
	//
	//}
	//
	//expand(int digit, int places, int base)
	//{
	//	unsigned long int answer,multipler;
	//	int iterations;
	//	multipler = 1;
	//	for (iterations = 1;iterations <= places ;iterations++)
	//	{
	//		multipler=multipler*base;
	//	}
	//
	//	answer=digit*multipler;
	//
	//	return(answer);
	//}
	//
	//dec_to_integer(int string_form[20],unsigned long int *number_form)
	//{
	//	int char_position, number, digit, string_form_length,a;
	//
	//	char_position = strlen(string_form)-1;
	//	string_form_length = char_position;
	//	*number_form = 0;
	//
	//	while (char_position >= 0)
	//	{
	//
	//		if ((string_form[char_position] >= '0' && string_form[char_position] <= '9'))
	//		{
	//			digit = string_form[char_position]-48;
	//		}
	//		else
	//		{
	//			log_error(12);
	//			return(0);
	//		}
	//
	//		a=expand(digit,string_form_length - char_position,10);
	//		*number_form = *number_form + a;
	//		char_position--;
	//	}
	//
	//	return(1);
	//}
	//
	//hex_to_integer(int string_form[20],unsigned long int *number_form)
	//{
	//	int char_position, number, digit, string_form_length,a;
	//
	//	char_position = strlen(string_form)-1;
	//	string_form_length = char_position;
	//	*number_form = 0;
	//
	//	while (char_position >= 0)
	//	{
	//
	//		if ((string_form[char_position] >= '0' && string_form[char_position] <= '9'))
	//		{
	//			digit = string_form[char_position]-48;
	//		}
	//		else if ((string_form[char_position] >= 'A' && string_form[char_position] <= 'F'))
	//		{
	//			digit = string_form[char_position]-55;
	//		}
	//		else if ((string_form[char_position] >= 'a' && string_form[char_position] <= 'f'))
	//		{
	//			digit = string_form[char_position]-87;
	//		}
	//		else
	//		{
	//			log_error(12);
	//			return(0);
	//		}
	//
	//		a=expand(digit,string_form_length - char_position,16);
	//		*number_form = *number_form + a;
	//		char_position--;
	//	}
	//
	//	return(1);
	//}
	//
	//
	//bin_to_integer(int string_form[20],unsigned long int *number_form)
	//{
	//	int char_position, number, digit, string_form_length,a;
	//
	//	char_position = strlen(string_form)-1;
	//	string_form_length = char_position;
	//	*number_form = 0;
	//
	//	while (char_position >= 0)
	//	{
	//
	//		if ((string_form[char_position] >= '0' && string_form[char_position] <= '1'))
	//		{
	//			digit = string_form[char_position]-48;
	//		}
	//		else
	//		{
	//			log_error(12);
	//			return(0);
	//		}
	//
	//		a=expand(digit,string_form_length - char_position,2);
	//		*number_form = *number_form + a;
	//		char_position--;
	//	}
	//
	//	return(1);
	//}
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

	//
	//is_number(int ascii_hold[])
	//{
	//	int number_base_position,char_pos;
	//	int number_base, ascii_hold_copy[20];
	//
	//	if (ascii_hold[0] < '0' || ascii_hold[0] > '9')
	//		{
	//			return(0);
	//		}
	//
	//	strcpy(ascii_hold_copy,ascii_hold);
	//	number_base_position = strlen(ascii_hold_copy)-1;
	//	number_base = ascii_hold_copy[number_base_position];
	//	ascii_hold_copy[number_base_position] = 0;
	//	char_pos = strlen(ascii_hold_copy)-1;
	//
	//	if (number_base == 'B' || number_base == 'b')
	//	{
	//		while (char_pos >= 0)
	//		{
	//			if (ascii_hold_copy[char_pos] < '0' || ascii_hold_copy[char_pos] > '1')
	//			{
	//				return(0);
	//			}
	//			char_pos--;
	//		}
	//		return(1);
	//	}
	//	else if (number_base == 'D' || number_base == 'd')
	//	{
	//		while (char_pos >= 0)
	//		{
	//			if (ascii_hold_copy[char_pos] < '0' || ascii_hold_copy[char_pos] > '9')
	//			{
	//				return(0);
	//			}
	//			char_pos--;
	//		}
	//		return(1);
	//	}
	//	else if (number_base == 'H' || number_base == 'h')
	//	{
	//		while (char_pos >= 0)
	//		{
	//			if (ascii_hold_copy[char_pos] >= '0' && ascii_hold_copy[char_pos] <= '9')
	//			{
	//			}
	//			else if (ascii_hold_copy[char_pos] >= 'A' && ascii_hold_copy[char_pos] <= 'Z')
	//			{
	//			}
	//			else if (ascii_hold_copy[char_pos] >= 'a' && ascii_hold_copy[char_pos] <= 'z')
	//			{
	//			}
	//			else
	//			{
	//				return(0);
	//			}
	//			char_pos--;
	//		}
	//		return(1);
	//	}
	//	else
	//	{
	//		return(0);
	//	}
	//
	//
	//}
	//
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

	//
	//check_operand_character(int character)
	//{
	//	if (character >= ' ' && character <= 'z')
	//		{
	//			return(1);
	//		}
	//		else
	//		{
	//			return(0);
	//		}
	//}
	//

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
	//	error_index++;
	//	error_table[error_index].line_number = source_line_number;
	//	error_table[error_index].line_index = line_index;
	//	error_table[error_index].error_number = error_number;
	//
	//	if (pass2_flag && error_table[error_index-1].line_number != source_line_number)
	//	{
	//		no_lc_flag = 1;
	//		no_obj_flag = 1;
	//		print_line();
	//	}
	//	else if (pass2_flag)
	//	{
	//		no_lc_flag = 1;
	//		no_obj_flag = 1;
	//		no_line_num_flag = 1;
	//		no_source_flag = 1;
	//		print_line();
	//	}
	//	else
	//	{
	//		no_obj_flag=1;
	//		print_line();
	//
	//		no_lc_flag = 1;
	//		no_obj_flag = 1;
	//		no_line_num_flag = 1;
	//		no_source_flag = 1;
	//		print_line();
	//	}
	//}
	//
	//
	//log_label()
	//{
	//	if (pass2_flag == 0)
	//	{
	//		if (scan_for_symbol(hold_label))
	//		{
	//			sym_tbl_index++;
	//			strcpy(symbol_table[sym_tbl_index].label,hold_label);
	//			symbol_table[sym_tbl_index].address = location_counter;
	//			strcpy(symbol_table[sym_tbl_index+1].label,"XXX");
	//			return(1);
	//		}
	//		else
	//		{
	//			log_error(4);
	//			return(0);
	//		}
	//	}
	//	else
	//	{
	//		return(1);
	//	}
	//}
	//
	//
	//log_symbol()
	//{
	//	if (pass2_flag == 0)
	//	{
	//		if (scan_for_symbol(hold_label))
	//		{
	//			sym_tbl_index++;
	//			strcpy(symbol_table[sym_tbl_index].label,hold_label);
	//			symbol_table[sym_tbl_index].address = temp_converted_number;
	//			strcpy(symbol_table[sym_tbl_index+1].label,"XXX");
	//			return(1);
	//		}
	//		else
	//		{
	//			log_error(4);
	//			return(0);
	//		}
	//	}
	//	else
	//	{
	//		return(1);
	//	}
	}//end method}}}
	//
	//
	//print_line()
	//{
	//	int local_index;
	//	int lst_line[150],buffer[150],loc_cntr[10],obj_code[10],line_num[10],source[132],error_line[80];
	//	local_index = 0;
	//	lst_line[0] = 0;
	//	loc_cntr[0] = 0;
	//	obj_code[0] = 0;
	//	line_num[0] = 0;
	//	source[0] = 0;
	//	error_line[0] = 0;
	//
	//	if (pass2_flag)
	//	{
	//		build_s_records();
	//	}
	//
	//	if (lst_flag == 1)
	//	{
	//		if (no_lc_flag == 0)
	//		{
	//			sprintf(loc_cntr,"\n%.4lX ",location_counter);
	//		}
	//		else if (error_table[print_error_index-1].line_number != source_line_number )
	//		{
	//			no_lc_flag =0;
	//			no_obj_flag =1;
	//			sprintf(loc_cntr,"\n     ");
	//		}
	//
	//		if (no_obj_flag == 0)
	//		{
	//			if (hold_obj_1 > -1 && hold_obj_2 == -1 && hold_obj_3 == -1)
	//			{
	//				sprintf(obj_code,"%.2X      ",hold_obj_1);
	//				hold_obj_1=-1;
	//			}
	//			else if (hold_obj_1 > -1 && hold_obj_2 > -1 && hold_obj_3 == -1)
	//			{
	//				sprintf(obj_code,"%.2X %.2X   ",hold_obj_1,hold_obj_2);
	//				hold_obj_1=-1;
	//				hold_obj_2=-1;
	//			}
	//			else if (hold_obj_1 > -1 && hold_obj_2 > -1 && hold_obj_3 > -1)
	//			{
	//				sprintf(obj_code,"%.2X %.2X %.2X",hold_obj_1,hold_obj_2,hold_obj_3);
	//				hold_obj_1=-1;
	//				hold_obj_2=-1;
	//				hold_obj_3=-1;
	//			}
	//			else
	//			{
	//				log_error(31);
	//				return(0);
	//			}
	//		}
	//		else
	//		{
	//			no_obj_flag = 0;
	//			sprintf(obj_code,"        ");
	//		}
	//
	//		if (no_line_num_flag ==1)
	//		{
	//			no_line_num_flag = 0;
	//			sprintf(line_num,"     ");
	//		}
	//		else
	//		{
	//			sprintf(line_num,"%.6d |",source_line_number);
	//		}
	//
	//
	//
	//		if (no_source_flag == 1)
	//		{
	//			no_source_flag = 0;
	//			sprintf(source," ");
	//		}
	//		else
	//		{
	//			sprintf(source,"%s",source_line);
	//		}
	//		strcat(lst_line,loc_cntr);
	//		strcat(lst_line,obj_code);
	//		strcat(lst_line,"   ");
	//		strcat(lst_line,line_num);
	//		strcat(lst_line,source);
	//
	//
	///*		printf("%s",lst_line);*/
	//
	//		fputs(lst_line,lst_file_ptr);
	//
	//		while (error_table[print_error_index].line_number == source_line_number)
	//		{
	//			sprintf(error_line,"\n*** ERROR in line %d, Error #%d (%s) ***",error_table[print_error_index].line_number,error_table[print_error_index].error_number, error_message[error_table[print_error_index].error_number]);
	//			printf("%s",error_line);
	//			fputs(error_line,lst_file_ptr);
	//			print_error_index++;
	//
	//		}
	//	}
	//	else
	//	{
	//		no_source_flag = 0;
	//		no_lc_flag = 0;
	//		no_obj_flag = 0;
	//		no_line_num_flag = 0;
	//		hold_obj_1=-1;
	//		hold_obj_2=-1;
	//		hold_obj_3=-1;
	//	}
	//}
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
	//
	//count_words()
	//{
	//int index,comma_count;
	//
	//	index=0;
	//	comma_count=0;
	//	while(hold_operand[index] != 0)
	//	{
	//		if (hold_operand[index] == ',')
	//		{
	//			comma_count++;
	//		}
	//	index++;
	//	}
	//	comma_count++;
	//	location_counter=location_counter + (comma_count*2);
	//}
	//
	//
	//process_byte_duplicates()
	//{
	//	unsigned long int multipler,number;
	//	int ascii_multipler[20],ascii_number[20];
	//	int local_index,count,byte_count;
	//	local_index = 0;
	//	operand_index = 0;
	//	byte_count = 0;
	//
	//	while (hold_operand[operand_index] != '(')
	//	{
	//		ascii_multipler[local_index] = hold_operand[operand_index];
	//		local_index++;
	//		operand_index++;
	//		if(operand_index > 100)
	//		{
	//			log_error(22);
	//			return(0);
	//		}
	//	}
	//	ascii_multipler[local_index] = 0;
	//
	//	if (convert_to_number(ascii_multipler,&multipler))
	//	{
	//		local_index++;
	//		operand_index++;
	//		if (hold_operand[operand_index]==39)
	//		{
	//			local_index++;
	//			operand_index++;
	//			if (hold_operand[operand_index]==39)
	//			{
	//				log_error(32);
	//				return(0);
	//			}
	//			number=hold_operand[operand_index];
	//			hold_obj_1=number;
	//			print_line();
	//			location_counter++;
	//
	//			for (count=1;count< multipler;)
	//			{
	//				hold_obj_1 = number;
	//				byte_count++;
	//				count++;
	//
	//				if (count< multipler)
	//				{
	//					hold_obj_2 = number;
	//					byte_count++;
	//					count++;
	//				}
	//
	//				if (count< multipler)
	//				{
	//					hold_obj_3 = number;
	//					byte_count++;
	//					count++;
	//				}
	//
	//
	//				no_source_flag=1;
	//				no_line_num_flag=1;
	//				print_line();
	//				location_counter = location_counter + byte_count;
	//				byte_count = 0;
	//			}
	//
	//		}
	//		else if (hold_operand[operand_index] == '?')
	//		{
	//			hold_obj_1=0;
	//			print_line();
	//			location_counter++;
	//
	//			for (count=1;count< multipler;)
	//			{
	//				hold_obj_1 = 0;
	//				byte_count++;
	//				count++;
	//
	//				if (count< multipler)
	//				{
	//					hold_obj_2 = 0;
	//					byte_count++;
	//					count++;
	//				}
	//
	//				if (count< multipler)
	//				{
	//					hold_obj_3 = 0;
	//					byte_count++;
	//					count++;
	//				}
	//
	//
	//				no_source_flag=1;
	//				no_line_num_flag=1;
	//				print_line();
	//				location_counter = location_counter + byte_count;
	//				byte_count = 0;
	//			}
	//		}
	//		else
	//		{
	//			local_index=0;
	//			while (hold_operand[operand_index] != ')')
	//			{
	//				ascii_number[local_index] = hold_operand[operand_index];
	//				local_index++;
	//				operand_index++;
	//				if(operand_index > 100)
	//				{
	//					log_error(22);
	//					return(0);
	//				}
	//			}
	//			ascii_number[local_index] = 0;
	//
	//			if (convert_to_number(ascii_number,&number))
	//			{
	//			hold_obj_1=number;
	//			print_line();
	//			location_counter++;
	//
	//			for (count=1;count< multipler;)
	//			{
	//				hold_obj_1 = number;
	//				byte_count++;
	//				count++;
	//
	//				if (count< multipler)
	//				{
	//					hold_obj_2 = number;
	//					byte_count++;
	//					count++;
	//				}
	//
	//				if (count< multipler)
	//				{
	//					hold_obj_3 = number;
	//					byte_count++;
	//					count++;
	//				}
	//
	//
	//				no_source_flag=1;
	//				no_line_num_flag=1;
	//				print_line();
	//				location_counter = location_counter + byte_count;
	//				byte_count = 0;
	//			}
	//
	//			}
	//			else
	//			{
	//				log_error(12);
	//				return(0);
	//			}
	//
	//		}
	//		return(1);
	//	}
	//	else
	//	{
	//		log_error(21);
	//		return(0);
	//	}
	//}
	//
	//
	//process_word_duplicates()
	//{
	//	unsigned long int multipler,number;
	//	int ascii_multipler[20],ascii_number[20];
	//	int local_index,count;
	//	local_index = 0;
	//	operand_index = 0;
	//
	//	while (hold_operand[operand_index] != '(')
	//	{
	//		ascii_multipler[local_index] = hold_operand[operand_index];
	//		local_index++;
	//		operand_index++;
	//		if(operand_index > 100)
	//		{
	//			log_error(22);
	//			return(0);
	//		}
	//	}
	//	ascii_multipler[local_index] = 0;
	//
	//	if (convert_to_number(ascii_multipler,&multipler))
	//	{
	//		local_index++;
	//		operand_index++;
	//		if (hold_operand[operand_index]==39)
	//		{
	//			log_error(33);
	//			return(0);
	//		}
	//		else if (hold_operand[operand_index] == '?')
	//		{
	//			hold_obj_1=0;
	//			hold_obj_2=0;
	//			print_line();
	//
	//			location_counter+=2;
	//
	//			for (count=1;count< multipler;count++)
	//			{
	//				no_source_flag=1;
	//				no_line_num_flag=1;
	//				hold_obj_1=0;
	//				hold_obj_2=0;
	//				print_line();
	//				location_counter+=2;
	//			}
	//		}
	//		else
	//		{
	//			local_index=0;
	//			while (hold_operand[operand_index] != ')')
	//			{
	//				ascii_number[local_index] = hold_operand[operand_index];
	//				local_index++;
	//				operand_index++;
	//				if(operand_index > 100)
	//				{
	//					log_error(22);
	//					return(0);
	//				}
	//			}
	//			ascii_number[local_index] = 0;
	//
	//			if (convert_to_number(ascii_number,&number))
	//			{
	//			hold_obj_1=number & 255;
	//			hold_obj_2=((number >> 8) & 255);
	//			print_line();
	//			location_counter+=2;
	//
	//			for (count=1;count< multipler;count++)
	//			{
	//				no_source_flag=1;
	//				no_line_num_flag=1;
	//				hold_obj_1=number & 255;
	//				hold_obj_2=((number >> 8) & 255);
	//				print_line();
	//				location_counter+=2;
	//			}
	//
	//			}
	//			else
	//			{
	//				log_error(12);
	//				return(0);
	//			}
	//
	//		}
	//		return(1);
	//	}
	//	else
	//	{
	//		log_error(21);
	//		return(0);
	//	}
	//}
	//
	//
	//count_bytes()
	//{
	//int index,comma_count;
	//
	//	index=0;
	//	comma_count=0;
	//	while(hold_operand[index] != 0)
	//	{
	//		if (hold_operand[index] == ',')
	//		{
	//			comma_count++;
	//		}
	//		index++;
	//	}
	//	comma_count++;
	//	location_counter=location_counter + comma_count;
	//}
	//
	//
	//process_bytes()
	//{
	//	unsigned long int number;
	//	int local_index,operand_index,first_num_flag;
	//	int ascii_number[20];
	//
	//	operand_index=0;
	//	first_num_flag=0;
	//
	//	while(1)
	//	{
	//		local_index=0;
	//
	//		while (hold_operand[operand_index] != ',' && hold_operand[operand_index] !=0)
	//		{
	//			ascii_number[local_index]=hold_operand[operand_index];
	//			local_index++;
	//			operand_index++;
	//		}
	//		ascii_number[local_index] = 0;
	//
	//		if (ascii_number[0]=='?')
	//		{
	//			number = 0;
	//		}
	//		else if (convert_to_number(ascii_number,&number))
	//		{
	//		}
	//		else
	//		{
	//			log_error(12);
	//			return(0);
	//		}
	//
	//		if (first_num_flag == 0)
	//		{
	//			first_num_flag = 1;
	//			hold_obj_1=number;
	//			print_line();
	//			location_counter++;
	//		}
	//		else
	//		{
	//			no_source_flag=1;
	//			no_line_num_flag=1;
	//			hold_obj_1=number;
	//			print_line();
	//			location_counter++;
	//		}
	//
	//
	//		if (hold_operand[operand_index] != 0)
	//		{
	//		operand_index++;
	//		}
	//		else
	//		{
	//			return(1);
	//		}
	//
	//	}
	//}
	//
	//process_words()
	//{
	//	unsigned long int number;
	//	int local_index,operand_index,first_num_flag;
	//	int ascii_number[20];
	//
	//	operand_index=0;
	//	first_num_flag=0;
	//
	//	while(1)
	//	{
	//		local_index=0;
	//
	//		while (hold_operand[operand_index] != ',' && hold_operand[operand_index] !=0)
	//		{
	//			ascii_number[local_index]=hold_operand[operand_index];
	//			local_index++;
	//			operand_index++;
	//		}
	//		ascii_number[local_index] = 0;
	//
	//		if (ascii_number[0]=='?')
	//		{
	//			number = 0;
	//		}
	//		else if (convert_to_number(ascii_number,&number))
	//		{
	//		}
	//		else
	//		{
	//			log_error(12);
	//			return(0);
	//		}
	//
	//		if (first_num_flag == 0)
	//		{
	//			first_num_flag = 1;
	//			hold_obj_1 = number & 255;
	//			hold_obj_2 = ((number >> 8) & 255);
	//			print_line();
	//			location_counter+=2;
	//		}
	//		else
	//		{
	//			no_source_flag=1;
	//			no_line_num_flag=1;
	//			hold_obj_1 = number & 255;
	//			hold_obj_2 = ((number >> 8) & 255);
	//			print_line();
	//			location_counter+=2;
	//		}
	//
	//
	//		if (hold_operand[operand_index] != 0)
	//		{
	//		operand_index++;
	//		}
	//		else
	//		{
	//			return(1);
	//		}
	//
	//	}
	//}
	//
	//
	//process_ascii_characters()
	//{
	//	int index,byte_count;
	//
	//	index = 1;
	//	byte_count = 0;
	//
	//	hold_obj_1=hold_operand[index];
	//	print_line();
	//	index++;
	//	location_counter++;
	//
	//	while(hold_operand[index] != 34)
	//	{
	//		if (hold_operand[index] == 0)
	//		{
	//			log_error(23);
	//			return(0);
	//		}
	//
	//		hold_obj_1 = hold_operand[index];
	//		index++;
	//		byte_count++;
	//
	//		if (hold_operand[index] != 34)
	//		{
	//			hold_obj_2 = hold_operand[index];
	//			index++;
	//			byte_count++;
	//		}
	//
	//		if (hold_operand[index] != 34)
	//		{
	//			hold_obj_3 = hold_operand[index];
	//			index++;
	//			byte_count++;
	//		}
	//
	//		no_source_flag=1;
	//		no_line_num_flag=1;
	//		print_line();
	//		location_counter = location_counter + byte_count;
	//		byte_count = 0;
	//
	//	}
	//	return(1);
	//}
	//
	//
	//
	//count_ascii_characters()
	//{
	//	int index;
	//
	//	index = 1;
	//	if (hold_operand[index] == 34)
	//	{
	//		log_error(13);
	//		return(0);
	//	}
	//
	//	while(hold_operand[index] != 34)
	//	{
	//		if (hold_operand[index] == 0)
	//		{
	//			log_error(23);
	//			return(0);
	//		}
	//		index++;
	//		location_counter++;
	//	}
	//	return(1);
	//}
	//
	//
	//calculate_duplicates()
	//{
	//	unsigned long int multipler;
	//	int ascii_multipler[20];
	//	int index;
	//	index = 0;
	//
	//	while (hold_operand[index] != '(')
	//	{
	//		ascii_multipler[index] = hold_operand[index];
	//		index++;
	//		if(index > 100)
	//		{
	//			log_error(22);
	//			return(0);
	//		}
	//	}
	//	ascii_multipler[index] = 0;
	//	if (convert_to_number(ascii_multipler,&multipler))
	//	{
	//		location_counter=location_counter+ multipler;
	//		return(1);
	//	}
	//	else
	//	{
	//		log_error(21);
	//		return(0);
	//	}
	//}
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
	//	if (strstr(file_name,".asm") || strstr(file_name,".ASM"))
	//	{
	//		return(1);
	//	}
	//	else
	//	{
	//		return(0);
	//	}
	//}
	//
	//open_file(int file_name[30],FILE **file_pointer)
	//{
	//	int return_code;
	//
	//	if ((*file_pointer = fopen( file_name,"r")) != 0)
	//	{
	//		return_code=1;
	//	}
	//	else
	//	{
	//		return_code=0;
	//	}
	//	return(return_code);
	//}
	//
	//
	//open_lst_file()
	//{
	//	int local_index;
	//	local_index = 0;
	//	strcpy (lst_filename,source_file_name);
	//
	//	while ( lst_filename[local_index] != '.' )
	//	{
	//		local_index++;
	//		if (local_index > 29)
	//		{
	//			printf("\n\nInternal error, problem with lst file name.\n\n");
	//			return(0);
	//		}
	//
	//	}
	//	lst_filename[local_index] = 0;
	//	strcat(lst_filename,".lst");
	//
	//	lst_file_ptr = fopen( lst_filename,"w");
	//
	//
	//}


	private boolean read_operator_table()
	{
		op_table.put("LOF", new op_tbl("LOF\u0000".toCharArray() , "IMP\u0000".toCharArray(), 0x00, 0, 0));
		op_table.put("ORG", new op_tbl("ORG\u0000".toCharArray() , "DIR\u0000".toCharArray(), 0x00, 0, 0));
		op_table.put("*"  , new op_tbl("*\u0000".toCharArray() , "IMP\u0000".toCharArray(), 0x00, 0, 0));
		op_table.put("EQU", new op_tbl("EQU\u0000".toCharArray() , "DIR\u0000".toCharArray(), 0x00, 0, 0));
		op_table.put("DBT", new op_tbl("DBT\u0000".toCharArray() , "DIR\u0000".toCharArray(), 0x00, 0, 0));
		op_table.put("DWD", new op_tbl("DWD\u0000".toCharArray() , "DIR\u0000".toCharArray(), 0x00, 0, 0));
		op_table.put("END", new op_tbl("END\u0000".toCharArray() , "IMP\u0000".toCharArray(), 0x00, 0, 0));
		op_table.put("ADC", new op_tbl("ADC\u0000".toCharArray() , "IMM\u0000".toCharArray(), 0x69, 2, 2));
		op_table.put("ADC", new op_tbl("ADC\u0000".toCharArray() , "ABS\u0000".toCharArray(), 0x6D, 3, 4));
		op_table.put("ADC", new op_tbl("ADC\u0000".toCharArray() , "ABX\u0000".toCharArray(), 0x7D, 3, 4));
		op_table.put("ADC", new op_tbl("ADC\u0000".toCharArray() , "ABY\u0000".toCharArray(), 0x79, 3, 4));
		op_table.put("ADC", new op_tbl("ADC\u0000".toCharArray() , "IXR\u0000".toCharArray(), 0x61, 2, 6));
		op_table.put("ADC", new op_tbl("ADC\u0000".toCharArray() , "IRX\u0000".toCharArray(), 0x71, 2, 5));
		op_table.put("AND", new op_tbl("AND\u0000".toCharArray() , "IMM\u0000".toCharArray(), 0x29, 2, 2));
		op_table.put("AND", new op_tbl("AND\u0000".toCharArray() , "ABS\u0000".toCharArray(), 0x2D, 3, 4));
		op_table.put("AND", new op_tbl("AND\u0000".toCharArray() , "ABX\u0000".toCharArray(), 0x3D, 3, 4));
		op_table.put("AND", new op_tbl("AND\u0000".toCharArray() , "ABY\u0000".toCharArray(), 0x39, 3, 4));
		op_table.put("AND", new op_tbl("AND\u0000".toCharArray() , "IXR\u0000".toCharArray(), 0x21, 2, 6));
		op_table.put("AND", new op_tbl("AND\u0000".toCharArray() , "IRX\u0000".toCharArray(), 0x31, 2, 5));
		op_table.put("ASL", new op_tbl("ASL\u0000".toCharArray() , "ACC\u0000".toCharArray(), 0x0A, 1, 2));
		op_table.put("ASL", new op_tbl("ASL\u0000".toCharArray() , "ABS\u0000".toCharArray(), 0x0E, 3, 6));
		op_table.put("ASL", new op_tbl("ASL\u0000".toCharArray() , "ABX\u0000".toCharArray(), 0x1E, 3, 7));
		op_table.put("BCC", new op_tbl("BCC\u0000".toCharArray() , "REL\u0000".toCharArray(), 0x90, 2, 2));
		op_table.put("BCS", new op_tbl("BCS\u0000".toCharArray() , "REL\u0000".toCharArray(), 0xB0, 2, 2));
		op_table.put("BEQ", new op_tbl("BEQ\u0000".toCharArray() , "REL\u0000".toCharArray(), 0xF0, 2, 2));
		op_table.put("BIT", new op_tbl("BIT\u0000".toCharArray() , "ABS\u0000".toCharArray(), 0x2C, 3, 4));
		op_table.put("BMI", new op_tbl("BMI\u0000".toCharArray() , "REL\u0000".toCharArray(), 0x30, 2, 2));
		op_table.put("BNE", new op_tbl("BNE\u0000".toCharArray() , "REL\u0000".toCharArray(), 0xD0, 2, 2));
		op_table.put("BPL", new op_tbl("BPL\u0000".toCharArray() , "REL\u0000".toCharArray(), 0x10, 2, 2));
		op_table.put("BRK", new op_tbl("BRK\u0000".toCharArray() , "IMP\u0000".toCharArray(), 0x00, 1, 7));
		op_table.put("BVC", new op_tbl("BVC\u0000".toCharArray() , "REL\u0000".toCharArray(), 0x50, 2, 2));
		op_table.put("BVS", new op_tbl("BVS\u0000".toCharArray() , "REL\u0000".toCharArray(), 0x70, 2, 2));
		op_table.put("CLC", new op_tbl("CLC\u0000".toCharArray() , "IMP\u0000".toCharArray(), 0x18, 1, 2));
		op_table.put("CLD", new op_tbl("CLD\u0000".toCharArray() , "IMP\u0000".toCharArray(), 0xD8, 1, 2));
		op_table.put("CLI", new op_tbl("CLI\u0000".toCharArray() , "IMP\u0000".toCharArray(), 0x58, 1, 2));
		op_table.put("CLV", new op_tbl("CLV\u0000".toCharArray() , "IMP\u0000".toCharArray(), 0xB8, 1, 2));
		op_table.put("CMP", new op_tbl("CMP\u0000".toCharArray() , "IMM\u0000".toCharArray(), 0xC9, 2, 2));
		op_table.put("CMP", new op_tbl("CMP\u0000".toCharArray() , "ABS\u0000".toCharArray(), 0xCD, 3, 4));
		op_table.put("CMP", new op_tbl("CMP\u0000".toCharArray() , "ABX\u0000".toCharArray(), 0xDD, 3, 4));
		op_table.put("CMP", new op_tbl("CMP\u0000".toCharArray() , "ABY\u0000".toCharArray(), 0xD9, 3, 4));
		op_table.put("CMP", new op_tbl("CMP\u0000".toCharArray() , "IXR\u0000".toCharArray(), 0xC1, 2, 6));
		op_table.put("CMP", new op_tbl("CMP\u0000".toCharArray() , "IRX\u0000".toCharArray(), 0xD1, 2, 5));
		op_table.put("CPX", new op_tbl("CPX\u0000".toCharArray() , "IMM\u0000".toCharArray(), 0xE0, 2, 2));
		op_table.put("CPX", new op_tbl("CPX\u0000".toCharArray() , "ABS\u0000".toCharArray(), 0xEC, 3, 4));
		op_table.put("CPY", new op_tbl("CPY\u0000".toCharArray() , "IMM\u0000".toCharArray(), 0xC0, 2, 2));
		op_table.put("CPY", new op_tbl("CPY\u0000".toCharArray() , "ABS\u0000".toCharArray(), 0xCC, 3, 4));
		op_table.put("DEC", new op_tbl("DEC\u0000".toCharArray() , "ABS\u0000".toCharArray(), 0xCE, 3, 6));
		op_table.put("DEC", new op_tbl("DEC\u0000".toCharArray() , "ABX\u0000".toCharArray(), 0xDE, 3, 7));
		op_table.put("DEX", new op_tbl("DEX\u0000".toCharArray() , "IMP\u0000".toCharArray(), 0xCA, 1, 2));
		op_table.put("DEY", new op_tbl("DEY\u0000".toCharArray() , "IMP\u0000".toCharArray(), 0x88, 1, 2));
		op_table.put("EOR", new op_tbl("EOR\u0000".toCharArray() , "IMM\u0000".toCharArray(), 0x49, 2, 2));
		op_table.put("EOR", new op_tbl("EOR\u0000".toCharArray() , "ABS\u0000".toCharArray(), 0x4D, 3, 4));
		op_table.put("EOR", new op_tbl("EOR\u0000".toCharArray() , "ABX\u0000".toCharArray(), 0x5D, 3, 4));
		op_table.put("EOR", new op_tbl("EOR\u0000".toCharArray() , "ABY\u0000".toCharArray(), 0x59, 3, 4));
		op_table.put("EOR", new op_tbl("EOR\u0000".toCharArray() , "IXR\u0000".toCharArray(), 0x41, 2, 6));
		op_table.put("EOR", new op_tbl("EOR\u0000".toCharArray() , "IRX\u0000".toCharArray(), 0x51, 2, 5));
		op_table.put("INC", new op_tbl("INC\u0000".toCharArray() , "ABS\u0000".toCharArray(), 0xEE, 3, 6));
		op_table.put("INC", new op_tbl("INC\u0000".toCharArray() , "ABX\u0000".toCharArray(), 0xFE, 3, 7));
		op_table.put("INX", new op_tbl("INX\u0000".toCharArray() , "IMP\u0000".toCharArray(), 0xE8, 1, 2));
		op_table.put("INY", new op_tbl("INY\u0000".toCharArray() , "IMP\u0000".toCharArray(), 0xC8, 1, 2));
		op_table.put("JMP", new op_tbl("JMP\u0000".toCharArray() , "ABS\u0000".toCharArray(), 0x4C, 3, 3));
		op_table.put("JMP", new op_tbl("JMP\u0000".toCharArray() , "IND\u0000".toCharArray(), 0x6C, 3, 5));
		op_table.put("JSR", new op_tbl("JSR\u0000".toCharArray() , "ABS\u0000".toCharArray(), 0x20, 3, 6));
		op_table.put("LDA", new op_tbl("LDA\u0000".toCharArray() , "IMM\u0000".toCharArray(), 0xA9, 2, 2));
		op_table.put("LDA", new op_tbl("LDA\u0000".toCharArray() , "ABS\u0000".toCharArray(), 0xAD, 3, 4));
		op_table.put("LDA", new op_tbl("LDA\u0000".toCharArray() , "ABX\u0000".toCharArray(), 0xBD, 3, 4));
		op_table.put("LDA", new op_tbl("LDA\u0000".toCharArray() , "ABY\u0000".toCharArray(), 0xB9, 3, 4));
		op_table.put("LDA", new op_tbl("LDA\u0000".toCharArray() , "IXR\u0000".toCharArray(), 0xA1, 2, 6));
		op_table.put("LDA", new op_tbl("LDA\u0000".toCharArray() , "IRX\u0000".toCharArray(), 0xB1, 2, 5));
		op_table.put("LDX", new op_tbl("LDX\u0000".toCharArray() , "IMM\u0000".toCharArray(), 0xA2, 2, 2));
		op_table.put("LDX", new op_tbl("LDX\u0000".toCharArray() , "ABS\u0000".toCharArray(), 0xAE, 3, 4));
		op_table.put("LDX", new op_tbl("LDX\u0000".toCharArray() , "ABY\u0000".toCharArray(), 0xBE, 3, 4));
		op_table.put("LDY", new op_tbl("LDY\u0000".toCharArray() , "IMM\u0000".toCharArray(), 0xA0, 2, 2));
		op_table.put("LDY", new op_tbl("LDY\u0000".toCharArray() , "ABS\u0000".toCharArray(), 0xAC, 3, 4));
		op_table.put("LDY", new op_tbl("LDY\u0000".toCharArray() , "ABX\u0000".toCharArray(), 0xBC, 3, 4));
		op_table.put("LSR", new op_tbl("LSR\u0000".toCharArray() , "ACC\u0000".toCharArray(), 0x4A, 1, 2));
		op_table.put("LSR", new op_tbl("LSR\u0000".toCharArray() , "ABS\u0000".toCharArray(), 0x4E, 3, 6));
		op_table.put("LSR", new op_tbl("LSR\u0000".toCharArray() , "ABX\u0000".toCharArray(), 0x5E, 3, 7));
		op_table.put("NOP", new op_tbl("NOP\u0000".toCharArray() , "IMP\u0000".toCharArray(), 0xEA, 1, 2));
		op_table.put("ORA", new op_tbl("ORA\u0000".toCharArray() , "IMM\u0000".toCharArray(), 0x09, 2, 2));
		op_table.put("ORA", new op_tbl("ORA\u0000".toCharArray() , "ABS\u0000".toCharArray(), 0x0D, 3, 4));
		op_table.put("ORA", new op_tbl("ORA\u0000".toCharArray() , "ABX\u0000".toCharArray(), 0x1D, 3, 4));
		op_table.put("ORA", new op_tbl("ORA\u0000".toCharArray() , "ABY\u0000".toCharArray(), 0x19, 3, 4));
		op_table.put("ORA", new op_tbl("ORA\u0000".toCharArray() , "IXR\u0000".toCharArray(), 0x01, 2, 6));
		op_table.put("ORA", new op_tbl("ORA\u0000".toCharArray() , "IRX\u0000".toCharArray(), 0x11, 2, 5));
		op_table.put("PHA", new op_tbl("PHA\u0000".toCharArray() , "IMP\u0000".toCharArray(), 0x48, 1, 3));
		op_table.put("PHP", new op_tbl("PHP\u0000".toCharArray() , "IMP\u0000".toCharArray(), 0x08, 1, 3));
		op_table.put("PLA", new op_tbl("PLA\u0000".toCharArray() , "IMP\u0000".toCharArray(), 0x68, 1, 4));
		op_table.put("PLP", new op_tbl("PLP\u0000".toCharArray() , "IMP\u0000".toCharArray(), 0x28, 1, 4));
		op_table.put("ROL", new op_tbl("ROL\u0000".toCharArray() , "ACC\u0000".toCharArray(), 0x2A, 1, 2));
		op_table.put("ROL", new op_tbl("ROL\u0000".toCharArray() , "ABS\u0000".toCharArray(), 0x2E, 3, 6));
		op_table.put("ROL", new op_tbl("ROL\u0000".toCharArray() , "ABX\u0000".toCharArray(), 0x3E, 3, 7));
		op_table.put("ROR", new op_tbl("ROR\u0000".toCharArray() , "ACC\u0000".toCharArray(), 0x6A, 1, 2));
		op_table.put("ROR", new op_tbl("ROR\u0000".toCharArray() , "ABS\u0000".toCharArray(), 0x6E, 3, 6));
		op_table.put("ROR", new op_tbl("ROR\u0000".toCharArray() , "ABX\u0000".toCharArray(), 0x7E, 3, 7));
		op_table.put("RTI", new op_tbl("RTI\u0000".toCharArray() , "IMP\u0000".toCharArray(), 0x40, 1, 6));
		op_table.put("RTS", new op_tbl("RTS\u0000".toCharArray() , "IMP\u0000".toCharArray(), 0x60, 1, 6));
		op_table.put("SBC", new op_tbl("SBC\u0000".toCharArray() , "IMM\u0000".toCharArray(), 0xE9, 2, 2));
		op_table.put("SBC", new op_tbl("SBC\u0000".toCharArray() , "ABS\u0000".toCharArray(), 0xED, 3, 4));
		op_table.put("SBC", new op_tbl("SBC\u0000".toCharArray() , "ABX\u0000".toCharArray(), 0xFD, 3, 4));
		op_table.put("SBC", new op_tbl("SBC\u0000".toCharArray() , "ABY\u0000".toCharArray(), 0xF9, 3, 4));
		op_table.put("SBC", new op_tbl("SBC\u0000".toCharArray() , "IXR\u0000".toCharArray(), 0xE1, 2, 6));
		op_table.put("SBC", new op_tbl("SBC\u0000".toCharArray() , "IRX\u0000".toCharArray(), 0xF1, 2, 5));
		op_table.put("SEC", new op_tbl("SEC\u0000".toCharArray() , "IMP\u0000".toCharArray(), 0x38, 1, 2));
		op_table.put("SED", new op_tbl("SED\u0000".toCharArray() , "IMP\u0000".toCharArray(), 0xF8, 1, 2));
		op_table.put("SEI", new op_tbl("SEI\u0000".toCharArray() , "IMP\u0000".toCharArray(), 0x78, 1, 2));
		op_table.put("STA", new op_tbl("STA\u0000".toCharArray() , "ABS\u0000".toCharArray(), 0x8D, 3, 4));
		op_table.put("STA", new op_tbl("STA\u0000".toCharArray() , "ABX\u0000".toCharArray(), 0x9D, 3, 5));
		op_table.put("STA", new op_tbl("STA\u0000".toCharArray() , "ABY\u0000".toCharArray(), 0x99, 3, 5));
		op_table.put("STA", new op_tbl("STA\u0000".toCharArray() , "IXR\u0000".toCharArray(), 0x81, 2, 6));
		op_table.put("STA", new op_tbl("STA\u0000".toCharArray() , "IRX\u0000".toCharArray(), 0x91, 2, 6));
		op_table.put("STX", new op_tbl("STX\u0000".toCharArray() , "ABS\u0000".toCharArray(), 0x8E, 3, 4));
		op_table.put("STY", new op_tbl("STY\u0000".toCharArray() , "ABS\u0000".toCharArray(), 0x8C, 3, 4));
		op_table.put("TAX", new op_tbl("TAX\u0000".toCharArray() , "IMP\u0000".toCharArray(), 0xAA, 1, 2));
		op_table.put("TAY", new op_tbl("TAY\u0000".toCharArray() , "IMP\u0000".toCharArray(), 0xA8, 1, 2));
		op_table.put("TSX", new op_tbl("TSX\u0000".toCharArray() , "IMP\u0000".toCharArray(), 0xBA, 1, 2));
		op_table.put("TXA", new op_tbl("TXA\u0000".toCharArray() , "IMP\u0000".toCharArray(), 0x8A, 1, 2));
		op_table.put("TXS", new op_tbl("TXS\u0000".toCharArray() , "IMP\u0000".toCharArray(), 0x9A, 1, 2));
		op_table.put("TYA", new op_tbl("TYA\u0000".toCharArray() , "IMP\u0000".toCharArray(), 0x98, 1, 2));
		op_table.put("XXX", new op_tbl("XXX\u0000".toCharArray() , "XXX\u0000".toCharArray(), 0x00, 0, 0));
		op_table.put("XXX", new op_tbl("XXX\u0000".toCharArray() , "XXX\u0000".toCharArray(), 0x00, 0, 0));
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
			strcpy (op_table[x].operator, "XXX");
			
			
			/*for (x=0;(strcmp(op_table[x].operator,"end")!= 0);x++)
				printf("%s %s %x %x\n",op_table[x].operator,op_table[x].add_code,op_table[x].opcode,op_table[x].num_bytes);
			*/
		/*
			fclose(op_table_file_pointer);
			return(1);
			
	}
		else
		{
			return(0);
	}    
		*/

	}

	////{{{ build_s_records
	//build_s_records()
	//{
	//	/*Note: currently this code just skips dbts with questions marks. the
	//	reason for this was to make the code romable.
	//	*/
	//	if ((hold_operand[operand_index] == '?') || (strcmp(hold_operand,"?")==0))
	//	{
	//
	//		if (s_record[s_record_index].record_length == 3)
	//		{
	//			s_record[s_record_index].address = location_counter;
	//
	//			if (strcmp(hold_operator,"DBT")==0)
	//			{
	//				s_record[s_record_index].address++;
	//			}
	//			else
	//			{
	//				s_record[s_record_index].address+=2;
	//			}
	//		}
	//		return(1);
	//	}
	//
	//
	//	if (strcmp(hold_operator,"ORG")==0)
	//	{
	//		s_record_index++;
	//		code_index=0;
	//		s_record[s_record_index].record_length = 3;
	//		s_record[s_record_index].address = location_counter;
	//
	//		/* Note: Temporary - for Understandable 6502 computer only. */
	//		//if (s_record[s_record_index].address < 0x5000)
	//		//{
	//		//	s_record[s_record_index].address = s_record[s_record_index].address+ 0xbc00;
	//		//}
	//
	//
	//		/* if (s_record[s_record_index].address < 0x1000)
	//		{
	//		 	s_record[s_record_index].address = 0x1000;
	//		}
	//		*/
	//
	//	}
	//	else
	//	{
	//		if (hold_obj_1 > -1)
	//		{
	//			s_record[s_record_index].code[code_index] = hold_obj_1;
	//			code_index++;
	//			if (code_index >= 20)
	//			{
	//				s_record_index++;
	//				code_index = 0;
	//				s_record[s_record_index].record_length = 3;
	//				s_record[s_record_index].address = s_record[s_record_index-1].address + s_record[s_record_index-1].record_length-2;
	//			}
	//			else
	//			{
	//				s_record[s_record_index].record_length++;
	//			}
	//		}
	//		if (hold_obj_2 > -1)
	//		{
	//			s_record[s_record_index].code[code_index] = hold_obj_2;
	//			code_index++;
	//			if (code_index >= 20)
	//			{
	//				s_record_index++;
	//				code_index = 0;
	//				s_record[s_record_index].record_length = 3;
	//				s_record[s_record_index].address = s_record[s_record_index-1].address + s_record[s_record_index-1].record_length-2;
	//			}
	//			else
	//			{
	//				s_record[s_record_index].record_length++;
	//			}
	//		}
	//		if (hold_obj_3 > -1)
	//		{
	//			s_record[s_record_index].code[code_index] = hold_obj_3;
	//			code_index++;
	//			if (code_index >= 20)
	//			{
	//				s_record_index++;
	//				code_index = 0;
	//				s_record[s_record_index].record_length = 3;
	//				s_record[s_record_index].address = s_record[s_record_index-1].address + s_record[s_record_index-1].record_length-2;
	//			}
	//			else
	//			{
	//				s_record[s_record_index].record_length++;
	//			}
	//		}
	//	}
	//}// }}}
	//
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
	//			return(0);
	//		}
	//
	//	}
	//	s_rec_filename[local_index] = 0;
	//	strcat(s_rec_filename,".s19");
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
	//		sprintf(s_rec_line,"S1%.2X%.4lX",s_record[sr_index].record_length,s_record[sr_index].address);
	//
	//		checksum_accumulator = checksum_accumulator + s_record[sr_index].record_length;
	//		checksum_accumulator = checksum_accumulator + (s_record[sr_index].address & 255);
	//		checksum_accumulator = checksum_accumulator + ((s_record[sr_index].address >> 8) & 255);
	//
	//		for (code_index = 0; code_index < s_record[sr_index].record_length - 3; code_index++)
	//		{
	//			checksum_accumulator = checksum_accumulator + s_record[sr_index].code[code_index];
	//			sprintf(hold_code,"%.2X",s_record[sr_index].code[code_index]);
	//			strcat(s_rec_line,hold_code);
	//		}
	//		checksum = (~checksum_accumulator) & 255;
	//		sprintf(hold_code,"%.2X",checksum);
	//		strcat(s_rec_line,hold_code);
	//		strcat(s_rec_line,"\n");
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
	//			return(0);
	//		}
	//
	//	}
	//	sym_file_name[local_index] = 0;
	//	strcat(sym_file_name,".sym");
	//
	//	sym_file_ptr = fopen( sym_file_name,"w");
	//
	//
	//	for (x=1;x<=sym_tbl_index;x++)
	//	{
	//		sprintf(sym_line,"%d %s %lx \n",x,symbol_table[x].label,symbol_table[x].address);
	//		fputs(sym_line,sym_file_ptr);
	//	}
	//
	//	fclose(sym_file_ptr);
	//
	//}
	//
	////}}}





}//end class


// :indentSize=4:lineSeparator=\n:noTabs=false:tabSize=4:folding=custom:collapseFolds=0:



