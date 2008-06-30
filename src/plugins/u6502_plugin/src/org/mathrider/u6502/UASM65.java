package org.mathrider.u6502;

import java.io.File;
import java.io.FileReader;
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

	private int line_index,sym_tbl_index,x,op_tbl_index,end_flag;
	private int error_index,source_line_number,symbol_index; //return_code.
	private int location_counter,hold_location;
	private Integer temp_converted_number;

	private java.io.PushbackReader source_file_pointer;
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
	private op_tbl[] op_table = new op_tbl[130];
	
	//struct sym_tbl symbol_table[600];
	//private ArrayList symbol_table = new ArrayList();
	private sym_tbl[] symbol_table = new sym_tbl[600];
	
	//struct error_tbl error_table[70];
	private error_tbl[] error_table = new error_tbl[70];
	
	//struct s_rec s_record[386];
	private ArrayList s_record = new ArrayList();



	public UASM65()
	{
		super();
		
		source_file = new File("c:/ted/checkouts/mathrider/src/plugins/u6502_plugin/src/scripts/test.asm");

		initialize();
		
		read_operator_table();


	}//end constructor


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
		if (assem.pass1())
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

		
		
    }//}}}

	



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
	
	
	private void strcpy( String destination, String source)
	{
		destination = new String(source);
	}
	//}}}
	
	//{{{strcat
	private void strcat( int[] one, int[] two)
	{
		//Note: not finished yet.
	} // end method.
	
	
	private void strcat( String one, String two)
	{
		one = one.concat(two);
	}//end method.
	
	//}}}
	
	
	//{{{strcmp
	private int strcmp(int[] first, String second)
	{
		return(0);
	}//}}}
	
	
	//{{{strcmp
	private int strcmp(int[] first, int[] second)
	{
		return(0);
	}//}}}
	
	
	//{{{strchr
	private boolean strchr(int[] string, String character)
	{
		return(false);
	}//}}}
	
	//{{{strlen
	private int strlen(int[] string)
	{
		return(0);
	}//}}}


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

	private boolean pass1()
	{

		pass2_flag=false;
		lst_flag = 1;
		//strcpy(symbol_table[1].label,"XXX"); Note: need to adjust this.

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
		
		
		/*
				for (x=1;x<=sym_tbl_index;x++)
				{
					printf("\n%d %s %lx",x,symbol_table[x].label,symbol_table[x].address);
		
				}
		*/
		
		
		
				if (error_index == 0 && end_flag == 1)
				{
					System.out.println("\n\nPass1 0 errors\n");
					return(true);
				}
				else
				{
					System.out.printf("\n\nPass1 %d errors.\n",error_index);
		
					/*
					for (x=1;x<=error_index;x++)
					{
						printf("\nline#: %d    ",error_table[x].line_number);
						printf("error number: %d",error_table[x].error_number);
					}
					*/
		
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
	//pass2()
	//{
	//	pass2_flag=true;
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
					source_line[line_index]=0;
					parse_line();
					line_index = 0;
					wipe_line();

				}
				else
				{
					try
					{
						source_file_pointer.unread(charRead); //Note: this might throw a not supported exception.
					}
					catch(IOException e)
					{
						e.printStackTrace();
					}

					source_line[line_index]=0;
					parse_line();
					line_index = 0;
					wipe_line();

				}
			}
			else if (line_character == 10)
			{
				source_line[line_index]=0;
				boolean return_code=parse_line();
				if (return_code != false && pass_flag == 1)
				{
					//interpret_line_pass1();
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
			if ((strcmp(op_table[op_tbl_index].operator,"XXX"))== 0)
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
			strcpy(hold_add_mode,"IMM");
		}
		else if ((hold_operand[0] == 'A' || hold_operand[0] == 'a') && hold_operand[1] == 0)
		{
			strcpy(hold_add_mode,"ACC");
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
		 				strcpy(hold_add_mode,"IXR");
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
		 						strcpy(hold_add_mode,"IRX");
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
		 				//local_index++; Note: this is an unreachable statement.
		 			}
		 			strcpy(hold_add_mode,"IND");
		 			return(true);
		 		}
		 		local_index++;
		 	}
			log_error(16);
			return(false);
		}
		else if (strcmp(hold_operand,"")==0)
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
		 				strcpy(hold_add_mode,"ABX");
		 				return(true);
		 			}
		 			else if (hold_operand[local_index+1] == 'y' || hold_operand[local_index+1] == 'Y')
		 			{
		 				strcpy(hold_add_mode,"ABY");
		 				return(true);
		 			}
		 		}
		 		local_index++;
		 	}
		 	if (strcmp(op_table[op_tbl_index].add_code,"REL")==0)
		 	{
		 		strcpy(hold_add_mode,"REL");
		 		return(true);
		 	}
		 	else
		 	{
		 		strcpy(hold_add_mode,"ABS");
		 	}
		}
		return(true);
	}//}}}

	
	
	
	//{{{interpret_line_pass1()
	boolean interpret_line_pass1()
	{
	
		if (hold_label[0] != 0 && (strcmp(hold_operator,"EQU")!=0))
		{
			log_label();
		}
	
	
		if (strcmp(hold_operator,"LON")==0)
		{
			lst_flag=1;
		}
		else if (strcmp(hold_operator,"LOF")==0)
		{
			lst_flag=0;
		}
		else if (strcmp(hold_operator,"ORG")==0)
		{
			Integer tmp_converted_number = new Integer(0);
			if (convert_to_number(hold_operand, tmp_converted_number)) //,&temp_converted_number))
			{
				this.temp_converted_number = tmp_converted_number;
				if (tmp_converted_number >=0 && tmp_converted_number <=65535)
				{
					location_counter=tmp_converted_number;
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
		else if (strcmp(hold_operator,"END")==0)
		{
			end_flag=1;
		}
		else if (strcmp(hold_operator,"*")==0)
		{
		;
		}
		else if (strcmp(hold_operator,"EQU")==0)
		{
			Integer tmp_converted_number = new Integer(0);
			if (convert_to_number(hold_operand, tmp_converted_number)) //,&temp_converted_number))
			{
				this.temp_converted_number = tmp_converted_number;
				log_symbol();
			}
			else
			{
				log_error(16);
				return(false);
			}
		}
		else if (strcmp(hold_operator,"DBT")==0)
		{
			if (hold_operand[0] == 34) /* Check for ASCII by finding " */
			{
				count_ascii_characters();
			}
			else if (strchr(hold_operand,"("))// && strrchr(hold_operand,'('))
			{
				calculate_duplicates();
			}
			else if (strchr(hold_operand,",")) //Note: was strrchr
			{
				count_bytes();
			}
			else if (strcmp(hold_operand,"?")==0)
			{
				location_counter++;
			}
			else if ((strchr(hold_operand,"D") || strchr(hold_operand,"B") || strchr(hold_operand,"H")) && hold_operand[0] != '#')
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
		else if (strcmp(hold_operator,"DWD")==0)
		{
			if (strchr(hold_operand,","))
			{
				count_words();
			}
			else if (strcmp(hold_operand,"?")==0)
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
	//boolean interpret_line_pass2()
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
	//		Integer tmp_converted_number = new Integer(0);
	//		if (convert_to_number(hold_operand, tmp_converted_number)) // ,&temp_converted_number))
	//		{
	//			this.temp_converted_number = tmp_converted_number;
	//			if (emp_converted_number >=0 && tmp_converted_number <=65535)
	//			{
	//				location_counter=tmp_converted_number;
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
			else if (strcmp(symbol_table[symbol_index].label,"XXX")==0)
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
	boolean convert_to_number(int string_form[], Integer number_form)//,unsigned long int *number_form)
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
		number_form = number_form & 65535;
		
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
	boolean dec_to_integer(int string_form[], Integer number_form)//,unsigned long int *number_form)
	{
		int char_position, number, digit, string_form_length, a;
	
		char_position = strlen(string_form)-1;
		string_form_length = char_position;
		number_form = 0;
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
			number_form += a;
			
			char_position--;
		}
	
		return(true);
	}//}}}


	//{{{hex_to_integer
	boolean hex_to_integer(int string_form[], Integer number_form)//,unsigned long int *number_form)
	{
		int char_position, number, digit, string_form_length,a;
	
		char_position = strlen(string_form)-1;
		string_form_length = char_position;
		number_form = 0;

	
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
			number_form += a;
			
			char_position--;
		}
	
		return(true);
	}//}}}

	
	
	//{{{ bin_to_integerr
	boolean bin_to_integer(int string_form[], Integer number_form)//,unsigned long int *number_form)
	{
		int char_position, number, digit, string_form_length,a;
	
		char_position = strlen(string_form)-1;
		string_form_length = char_position;
		number_form = 0;

	
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
			number_form += a;
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
				strcpy(symbol_table[sym_tbl_index+1].label,"XXX");
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
				strcpy(symbol_table[sym_tbl_index+1].label,"XXX");
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
	void print_line()
	{
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
	}
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
	
	//
	//process_byte_duplicates()
	//{
	//	unsigned long int multiplier,number;
	//	int ascii_multiplier[20],ascii_number[20];
	//	int local_index,count,byte_count;
	//	local_index = 0;
	//	operand_index = 0;
	//	byte_count = 0;
	//
	//	while (hold_operand[operand_index] != '(')
	//	{
	//		ascii_multiplier[local_index] = hold_operand[operand_index];
	//		local_index++;
	//		operand_index++;
	//		if(operand_index > 100)
	//		{
	//			log_error(22);
	//			return(0);
	//		}
	//	}
	//	ascii_multiplier[local_index] = 0;
	//
	//	if (convert_to_number(ascii_multiplier,&multiplier))
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
	//			for (count=1;count< multiplier;)
	//			{
	//				hold_obj_1 = number;
	//				byte_count++;
	//				count++;
	//
	//				if (count< multiplier)
	//				{
	//					hold_obj_2 = number;
	//					byte_count++;
	//					count++;
	//				}
	//
	//				if (count< multiplier)
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
	//			for (count=1;count< multiplier;)
	//			{
	//				hold_obj_1 = 0;
	//				byte_count++;
	//				count++;
	//
	//				if (count< multiplier)
	//				{
	//					hold_obj_2 = 0;
	//					byte_count++;
	//					count++;
	//				}
	//
	//				if (count< multiplier)
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
	//			for (count=1;count< multiplier;)
	//			{
	//				hold_obj_1 = number;
	//				byte_count++;
	//				count++;
	//
	//				if (count< multiplier)
	//				{
	//					hold_obj_2 = number;
	//					byte_count++;
	//					count++;
	//				}
	//
	//				if (count< multiplier)
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
	//	unsigned long int multiplier,number;
	//	int ascii_multiplier[20],ascii_number[20];
	//	int local_index,count;
	//	local_index = 0;
	//	operand_index = 0;
	//
	//	while (hold_operand[operand_index] != '(')
	//	{
	//		ascii_multiplier[local_index] = hold_operand[operand_index];
	//		local_index++;
	//		operand_index++;
	//		if(operand_index > 100)
	//		{
	//			log_error(22);
	//			return(0);
	//		}
	//	}
	//	ascii_multiplier[local_index] = 0;
	//
	//	if (convert_to_number(ascii_multiplier,&multiplier))
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
	//			for (count=1;count< multiplier;count++)
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
	//			for (count=1;count< multiplier;count++)
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
	
	//{{{count_bytes
	void count_bytes()
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
		location_counter=location_counter + comma_count;
	}//}}}
	
	
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
		Integer multiplier = new Integer(0);
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
			location_counter=location_counter+ multiplier;
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
	//{{{open_file
	java.io.PushbackReader open_file(File file)
	{
		java.io.PushbackReader file_pointer;
		try 
		{
			file_pointer = new java.io.PushbackReader(new FileReader(file));

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
	strcpy( op_table[   0].operator , "LON" ); strcpy( op_table[   0].add_code , "IMP" ); op_table[   0].opcode = 0x00; op_table[   0].num_bytes = 0; op_table[   0].base_cycles = 0;
	strcpy( op_table[   1].operator , "LOF" ); strcpy( op_table[   1].add_code , "IMP" ); op_table[   1].opcode = 0x00; op_table[   1].num_bytes = 0; op_table[   1].base_cycles = 0;
	strcpy( op_table[   2].operator , "ORG" ); strcpy( op_table[   2].add_code , "DIR" ); op_table[   2].opcode = 0x00; op_table[   2].num_bytes = 0; op_table[   2].base_cycles = 0;
	strcpy( op_table[   3].operator , "*" )  ; strcpy( op_table[   3].add_code , "IMP" ); op_table[   3].opcode = 0x00; op_table[   3].num_bytes = 0; op_table[   3].base_cycles = 0;
	strcpy( op_table[   4].operator , "EQU" ); strcpy( op_table[   4].add_code , "DIR" ); op_table[   4].opcode = 0x00; op_table[   4].num_bytes = 0; op_table[   4].base_cycles = 0;
	strcpy( op_table[   5].operator , "DBT" ); strcpy( op_table[   5].add_code , "DIR" ); op_table[   5].opcode = 0x00; op_table[   5].num_bytes = 0; op_table[   5].base_cycles = 0;
	strcpy( op_table[   6].operator , "DWD" ); strcpy( op_table[   6].add_code , "DIR" ); op_table[   6].opcode = 0x00; op_table[   6].num_bytes = 0; op_table[   6].base_cycles = 0;
	strcpy( op_table[   7].operator , "END" ); strcpy( op_table[   7].add_code , "IMP" ); op_table[   7].opcode = 0x00; op_table[   7].num_bytes = 0; op_table[   7].base_cycles = 0;
	strcpy( op_table[   8].operator , "ADC" ); strcpy( op_table[   8].add_code , "IMM" ); op_table[   8].opcode = 0x69; op_table[   8].num_bytes = 2; op_table[   8].base_cycles = 2;
	strcpy( op_table[   9].operator , "ADC" ); strcpy( op_table[   9].add_code , "ABS" ); op_table[   9].opcode = 0x6D; op_table[   9].num_bytes = 3; op_table[   9].base_cycles = 4;
	strcpy( op_table[  10].operator , "ADC" ); strcpy( op_table[  10].add_code , "ABX" ); op_table[  10].opcode = 0x7D; op_table[  10].num_bytes = 3; op_table[  10].base_cycles = 4;
	strcpy( op_table[  11].operator , "ADC" ); strcpy( op_table[  11].add_code , "ABY" ); op_table[  11].opcode = 0x79; op_table[  11].num_bytes = 3; op_table[  11].base_cycles = 4;
	strcpy( op_table[  12].operator , "ADC" ); strcpy( op_table[  12].add_code , "IXR" ); op_table[  12].opcode = 0x61; op_table[  12].num_bytes = 2; op_table[  12].base_cycles = 6;
	strcpy( op_table[  13].operator , "ADC" ); strcpy( op_table[  13].add_code , "IRX" ); op_table[  13].opcode = 0x71; op_table[  13].num_bytes = 2; op_table[  13].base_cycles = 5;
	strcpy( op_table[  14].operator , "AND" ); strcpy( op_table[  14].add_code , "IMM" ); op_table[  14].opcode = 0x29; op_table[  14].num_bytes = 2; op_table[  14].base_cycles = 2;
	strcpy( op_table[  15].operator , "AND" ); strcpy( op_table[  15].add_code , "ABS" ); op_table[  15].opcode = 0x2D; op_table[  15].num_bytes = 3; op_table[  15].base_cycles = 4;
	strcpy( op_table[  16].operator , "AND" ); strcpy( op_table[  16].add_code , "ABX" ); op_table[  16].opcode = 0x3D; op_table[  16].num_bytes = 3; op_table[  16].base_cycles = 4;
	strcpy( op_table[  17].operator , "AND" ); strcpy( op_table[  17].add_code , "ABY" ); op_table[  17].opcode = 0x39; op_table[  17].num_bytes = 3; op_table[  17].base_cycles = 4;
	strcpy( op_table[  18].operator , "AND" ); strcpy( op_table[  18].add_code , "IXR" ); op_table[  18].opcode = 0x21; op_table[  18].num_bytes = 2; op_table[  18].base_cycles = 6;
	strcpy( op_table[  19].operator , "AND" ); strcpy( op_table[  19].add_code , "IRX" ); op_table[  19].opcode = 0x31; op_table[  19].num_bytes = 2; op_table[  19].base_cycles = 5;
	strcpy( op_table[  20].operator , "ASL" ); strcpy( op_table[  20].add_code , "ACC" ); op_table[  20].opcode = 0x0A; op_table[  20].num_bytes = 1; op_table[  20].base_cycles = 2;
	strcpy( op_table[  21].operator , "ASL" ); strcpy( op_table[  21].add_code , "ABS" ); op_table[  21].opcode = 0x0E; op_table[  21].num_bytes = 3; op_table[  21].base_cycles = 6;
	strcpy( op_table[  22].operator , "ASL" ); strcpy( op_table[  22].add_code , "ABX" ); op_table[  22].opcode = 0x1E; op_table[  22].num_bytes = 3; op_table[  22].base_cycles = 7;
	strcpy( op_table[  23].operator , "BCC" ); strcpy( op_table[  23].add_code , "REL" ); op_table[  23].opcode = 0x90; op_table[  23].num_bytes = 2; op_table[  23].base_cycles = 2;
	strcpy( op_table[  24].operator , "BCS" ); strcpy( op_table[  24].add_code , "REL" ); op_table[  24].opcode = 0xB0; op_table[  24].num_bytes = 2; op_table[  24].base_cycles = 2;
	strcpy( op_table[  25].operator , "BEQ" ); strcpy( op_table[  25].add_code , "REL" ); op_table[  25].opcode = 0xF0; op_table[  25].num_bytes = 2; op_table[  25].base_cycles = 2;
	strcpy( op_table[  26].operator , "BIT" ); strcpy( op_table[  26].add_code , "ABS" ); op_table[  26].opcode = 0x2C; op_table[  26].num_bytes = 3; op_table[  26].base_cycles = 4;
	strcpy( op_table[  27].operator , "BMI" ); strcpy( op_table[  27].add_code , "REL" ); op_table[  27].opcode = 0x30; op_table[  27].num_bytes = 2; op_table[  27].base_cycles = 2;
	strcpy( op_table[  28].operator , "BNE" ); strcpy( op_table[  28].add_code , "REL" ); op_table[  28].opcode = 0xD0; op_table[  28].num_bytes = 2; op_table[  28].base_cycles = 2;
	strcpy( op_table[  29].operator , "BPL" ); strcpy( op_table[  29].add_code , "REL" ); op_table[  29].opcode = 0x10; op_table[  29].num_bytes = 2; op_table[  29].base_cycles = 2;
	strcpy( op_table[  30].operator , "BRK" ); strcpy( op_table[  30].add_code , "IMP" ); op_table[  30].opcode = 0x00; op_table[  30].num_bytes = 1; op_table[  30].base_cycles = 7;
	strcpy( op_table[  31].operator , "BVC" ); strcpy( op_table[  31].add_code , "REL" ); op_table[  31].opcode = 0x50; op_table[  31].num_bytes = 2; op_table[  31].base_cycles = 2;
	strcpy( op_table[  32].operator , "BVS" ); strcpy( op_table[  32].add_code , "REL" ); op_table[  32].opcode = 0x70; op_table[  32].num_bytes = 2; op_table[  32].base_cycles = 2;
	strcpy( op_table[  33].operator , "CLC" ); strcpy( op_table[  33].add_code , "IMP" ); op_table[  33].opcode = 0x18; op_table[  33].num_bytes = 1; op_table[  33].base_cycles = 2;
	strcpy( op_table[  34].operator , "CLD" ); strcpy( op_table[  34].add_code , "IMP" ); op_table[  34].opcode = 0xD8; op_table[  34].num_bytes = 1; op_table[  34].base_cycles = 2;
	strcpy( op_table[  35].operator , "CLI" ); strcpy( op_table[  35].add_code , "IMP" ); op_table[  35].opcode = 0x58; op_table[  35].num_bytes = 1; op_table[  35].base_cycles = 2;
	strcpy( op_table[  36].operator , "CLV" ); strcpy( op_table[  36].add_code , "IMP" ); op_table[  36].opcode = 0xB8; op_table[  36].num_bytes = 1; op_table[  36].base_cycles = 2;
	strcpy( op_table[  37].operator , "CMP" ); strcpy( op_table[  37].add_code , "IMM" ); op_table[  37].opcode = 0xC9; op_table[  37].num_bytes = 2; op_table[  37].base_cycles = 2;
	strcpy( op_table[  38].operator , "CMP" ); strcpy( op_table[  38].add_code , "ABS" ); op_table[  38].opcode = 0xCD; op_table[  38].num_bytes = 3; op_table[  38].base_cycles = 4;
	strcpy( op_table[  39].operator , "CMP" ); strcpy( op_table[  39].add_code , "ABX" ); op_table[  39].opcode = 0xDD; op_table[  39].num_bytes = 3; op_table[  39].base_cycles = 4;
	strcpy( op_table[  40].operator , "CMP" ); strcpy( op_table[  40].add_code , "ABY" ); op_table[  40].opcode = 0xD9; op_table[  40].num_bytes = 3; op_table[  40].base_cycles = 4;
	strcpy( op_table[  41].operator , "CMP" ); strcpy( op_table[  41].add_code , "IXR" ); op_table[  41].opcode = 0xC1; op_table[  41].num_bytes = 2; op_table[  41].base_cycles = 6;
	strcpy( op_table[  42].operator , "CMP" ); strcpy( op_table[  42].add_code , "IRX" ); op_table[  42].opcode = 0xD1; op_table[  42].num_bytes = 2; op_table[  42].base_cycles = 5;
	strcpy( op_table[  43].operator , "CPX" ); strcpy( op_table[  43].add_code , "IMM" ); op_table[  43].opcode = 0xE0; op_table[  43].num_bytes = 2; op_table[  43].base_cycles = 2;
	strcpy( op_table[  44].operator , "CPX" ); strcpy( op_table[  44].add_code , "ABS" ); op_table[  44].opcode = 0xEC; op_table[  44].num_bytes = 3; op_table[  44].base_cycles = 4;
	strcpy( op_table[  45].operator , "CPY" ); strcpy( op_table[  45].add_code , "IMM" ); op_table[  45].opcode = 0xC0; op_table[  45].num_bytes = 2; op_table[  45].base_cycles = 2;
	strcpy( op_table[  46].operator , "CPY" ); strcpy( op_table[  46].add_code , "ABS" ); op_table[  46].opcode = 0xCC; op_table[  46].num_bytes = 3; op_table[  46].base_cycles = 4;
	strcpy( op_table[  47].operator , "DEC" ); strcpy( op_table[  47].add_code , "ABS" ); op_table[  47].opcode = 0xCE; op_table[  47].num_bytes = 3; op_table[  47].base_cycles = 6;
	strcpy( op_table[  48].operator , "DEC" ); strcpy( op_table[  48].add_code , "ABX" ); op_table[  48].opcode = 0xDE; op_table[  48].num_bytes = 3; op_table[  48].base_cycles = 7;
	strcpy( op_table[  49].operator , "DEX" ); strcpy( op_table[  49].add_code , "IMP" ); op_table[  49].opcode = 0xCA; op_table[  49].num_bytes = 1; op_table[  49].base_cycles = 2;
	strcpy( op_table[  50].operator , "DEY" ); strcpy( op_table[  50].add_code , "IMP" ); op_table[  50].opcode = 0x88; op_table[  50].num_bytes = 1; op_table[  50].base_cycles = 2;
	strcpy( op_table[  51].operator , "EOR" ); strcpy( op_table[  51].add_code , "IMM" ); op_table[  51].opcode = 0x49; op_table[  51].num_bytes = 2; op_table[  51].base_cycles = 2;
	strcpy( op_table[  52].operator , "EOR" ); strcpy( op_table[  52].add_code , "ABS" ); op_table[  52].opcode = 0x4D; op_table[  52].num_bytes = 3; op_table[  52].base_cycles = 4;
	strcpy( op_table[  53].operator , "EOR" ); strcpy( op_table[  53].add_code , "ABX" ); op_table[  53].opcode = 0x5D; op_table[  53].num_bytes = 3; op_table[  53].base_cycles = 4;
	strcpy( op_table[  54].operator , "EOR" ); strcpy( op_table[  54].add_code , "ABY" ); op_table[  54].opcode = 0x59; op_table[  54].num_bytes = 3; op_table[  54].base_cycles = 4;
	strcpy( op_table[  55].operator , "EOR" ); strcpy( op_table[  55].add_code , "IXR" ); op_table[  55].opcode = 0x41; op_table[  55].num_bytes = 2; op_table[  55].base_cycles = 6;
	strcpy( op_table[  56].operator , "EOR" ); strcpy( op_table[  56].add_code , "IRX" ); op_table[  56].opcode = 0x51; op_table[  56].num_bytes = 2; op_table[  56].base_cycles = 5;
	strcpy( op_table[  57].operator , "INC" ); strcpy( op_table[  57].add_code , "ABS" ); op_table[  57].opcode = 0xEE; op_table[  57].num_bytes = 3; op_table[  57].base_cycles = 6;
	strcpy( op_table[  58].operator , "INC" ); strcpy( op_table[  58].add_code , "ABX" ); op_table[  58].opcode = 0xFE; op_table[  58].num_bytes = 3; op_table[  58].base_cycles = 7;
	strcpy( op_table[  59].operator , "INX" ); strcpy( op_table[  59].add_code , "IMP" ); op_table[  59].opcode = 0xE8; op_table[  59].num_bytes = 1; op_table[  59].base_cycles = 2;
	strcpy( op_table[  60].operator , "INY" ); strcpy( op_table[  60].add_code , "IMP" ); op_table[  60].opcode = 0xC8; op_table[  60].num_bytes = 1; op_table[  60].base_cycles = 2;
	strcpy( op_table[  61].operator , "JMP" ); strcpy( op_table[  61].add_code , "ABS" ); op_table[  61].opcode = 0x4C; op_table[  61].num_bytes = 3; op_table[  61].base_cycles = 3;
	strcpy( op_table[  62].operator , "JMP" ); strcpy( op_table[  62].add_code , "IND" ); op_table[  62].opcode = 0x6C; op_table[  62].num_bytes = 3; op_table[  62].base_cycles = 5;
	strcpy( op_table[  63].operator , "JSR" ); strcpy( op_table[  63].add_code , "ABS" ); op_table[  63].opcode = 0x20; op_table[  63].num_bytes = 3; op_table[  63].base_cycles = 6;
	strcpy( op_table[  64].operator , "LDA" ); strcpy( op_table[  64].add_code , "IMM" ); op_table[  64].opcode = 0xA9; op_table[  64].num_bytes = 2; op_table[  64].base_cycles = 2;
	strcpy( op_table[  65].operator , "LDA" ); strcpy( op_table[  65].add_code , "ABS" ); op_table[  65].opcode = 0xAD; op_table[  65].num_bytes = 3; op_table[  65].base_cycles = 4;
	strcpy( op_table[  66].operator , "LDA" ); strcpy( op_table[  66].add_code , "ABX" ); op_table[  66].opcode = 0xBD; op_table[  66].num_bytes = 3; op_table[  66].base_cycles = 4;
	strcpy( op_table[  67].operator , "LDA" ); strcpy( op_table[  67].add_code , "ABY" ); op_table[  67].opcode = 0xB9; op_table[  67].num_bytes = 3; op_table[  67].base_cycles = 4;
	strcpy( op_table[  68].operator , "LDA" ); strcpy( op_table[  68].add_code , "IXR" ); op_table[  68].opcode = 0xA1; op_table[  68].num_bytes = 2; op_table[  68].base_cycles = 6;
	strcpy( op_table[  69].operator , "LDA" ); strcpy( op_table[  69].add_code , "IRX" ); op_table[  69].opcode = 0xB1; op_table[  69].num_bytes = 2; op_table[  69].base_cycles = 5;
	strcpy( op_table[  70].operator , "LDX" ); strcpy( op_table[  70].add_code , "IMM" ); op_table[  70].opcode = 0xA2; op_table[  70].num_bytes = 2; op_table[  70].base_cycles = 2;
	strcpy( op_table[  71].operator , "LDX" ); strcpy( op_table[  71].add_code , "ABS" ); op_table[  71].opcode = 0xAE; op_table[  71].num_bytes = 3; op_table[  71].base_cycles = 4;
	strcpy( op_table[  72].operator , "LDX" ); strcpy( op_table[  72].add_code , "ABY" ); op_table[  72].opcode = 0xBE; op_table[  72].num_bytes = 3; op_table[  72].base_cycles = 4;
	strcpy( op_table[  73].operator , "LDY" ); strcpy( op_table[  73].add_code , "IMM" ); op_table[  73].opcode = 0xA0; op_table[  73].num_bytes = 2; op_table[  73].base_cycles = 2;
	strcpy( op_table[  74].operator , "LDY" ); strcpy( op_table[  74].add_code , "ABS" ); op_table[  74].opcode = 0xAC; op_table[  74].num_bytes = 3; op_table[  74].base_cycles = 4;
	strcpy( op_table[  75].operator , "LDY" ); strcpy( op_table[  75].add_code , "ABX" ); op_table[  75].opcode = 0xBC; op_table[  75].num_bytes = 3; op_table[  75].base_cycles = 4;
	strcpy( op_table[  76].operator , "LSR" ); strcpy( op_table[  76].add_code , "ACC" ); op_table[  76].opcode = 0x4A; op_table[  76].num_bytes = 1; op_table[  76].base_cycles = 2;
	strcpy( op_table[  77].operator , "LSR" ); strcpy( op_table[  77].add_code , "ABS" ); op_table[  77].opcode = 0x4E; op_table[  77].num_bytes = 3; op_table[  77].base_cycles = 6;
	strcpy( op_table[  78].operator , "LSR" ); strcpy( op_table[  78].add_code , "ABX" ); op_table[  78].opcode = 0x5E; op_table[  78].num_bytes = 3; op_table[  78].base_cycles = 7;
	strcpy( op_table[  79].operator , "NOP" ); strcpy( op_table[  79].add_code , "IMP" ); op_table[  79].opcode = 0xEA; op_table[  79].num_bytes = 1; op_table[  79].base_cycles = 2;
	strcpy( op_table[  80].operator , "ORA" ); strcpy( op_table[  80].add_code , "IMM" ); op_table[  80].opcode = 0x09; op_table[  80].num_bytes = 2; op_table[  80].base_cycles = 2;
	strcpy( op_table[  81].operator , "ORA" ); strcpy( op_table[  81].add_code , "ABS" ); op_table[  81].opcode = 0x0D; op_table[  81].num_bytes = 3; op_table[  81].base_cycles = 4;
	strcpy( op_table[  82].operator , "ORA" ); strcpy( op_table[  82].add_code , "ABX" ); op_table[  82].opcode = 0x1D; op_table[  82].num_bytes = 3; op_table[  82].base_cycles = 4;
	strcpy( op_table[  83].operator , "ORA" ); strcpy( op_table[  83].add_code , "ABY" ); op_table[  83].opcode = 0x19; op_table[  83].num_bytes = 3; op_table[  83].base_cycles = 4;
	strcpy( op_table[  84].operator , "ORA" ); strcpy( op_table[  84].add_code , "IXR" ); op_table[  84].opcode = 0x01; op_table[  84].num_bytes = 2; op_table[  84].base_cycles = 6;
	strcpy( op_table[  85].operator , "ORA" ); strcpy( op_table[  85].add_code , "IRX" ); op_table[  85].opcode = 0x11; op_table[  85].num_bytes = 2; op_table[  85].base_cycles = 5;
	strcpy( op_table[  86].operator , "PHA" ); strcpy( op_table[  86].add_code , "IMP" ); op_table[  86].opcode = 0x48; op_table[  86].num_bytes = 1; op_table[  86].base_cycles = 3;
	strcpy( op_table[  87].operator , "PHP" ); strcpy( op_table[  87].add_code , "IMP" ); op_table[  87].opcode = 0x08; op_table[  87].num_bytes = 1; op_table[  87].base_cycles = 3;
	strcpy( op_table[  88].operator , "PLA" ); strcpy( op_table[  88].add_code , "IMP" ); op_table[  88].opcode = 0x68; op_table[  88].num_bytes = 1; op_table[  88].base_cycles = 4;
	strcpy( op_table[  89].operator , "PLP" ); strcpy( op_table[  89].add_code , "IMP" ); op_table[  89].opcode = 0x28; op_table[  89].num_bytes = 1; op_table[  89].base_cycles = 4;
	strcpy( op_table[  90].operator , "ROL" ); strcpy( op_table[  90].add_code , "ACC" ); op_table[  90].opcode = 0x2A; op_table[  90].num_bytes = 1; op_table[  90].base_cycles = 2;
	strcpy( op_table[  91].operator , "ROL" ); strcpy( op_table[  91].add_code , "ABS" ); op_table[  91].opcode = 0x2E; op_table[  91].num_bytes = 3; op_table[  91].base_cycles = 6;
	strcpy( op_table[  92].operator , "ROL" ); strcpy( op_table[  92].add_code , "ABX" ); op_table[  92].opcode = 0x3E; op_table[  92].num_bytes = 3; op_table[  92].base_cycles = 7;
	strcpy( op_table[  93].operator , "ROR" ); strcpy( op_table[  93].add_code , "ACC" ); op_table[  93].opcode = 0x6A; op_table[  93].num_bytes = 1; op_table[  93].base_cycles = 2;
	strcpy( op_table[  94].operator , "ROR" ); strcpy( op_table[  94].add_code , "ABS" ); op_table[  94].opcode = 0x6E; op_table[  94].num_bytes = 3; op_table[  94].base_cycles = 6;
	strcpy( op_table[  95].operator , "ROR" ); strcpy( op_table[  95].add_code , "ABX" ); op_table[  95].opcode = 0x7E; op_table[  95].num_bytes = 3; op_table[  95].base_cycles = 7;
	strcpy( op_table[  96].operator , "RTI" ); strcpy( op_table[  96].add_code , "IMP" ); op_table[  96].opcode = 0x40; op_table[  96].num_bytes = 1; op_table[  96].base_cycles = 6;
	strcpy( op_table[  97].operator , "RTS" ); strcpy( op_table[  97].add_code , "IMP" ); op_table[  97].opcode = 0x60; op_table[  97].num_bytes = 1; op_table[  97].base_cycles = 6;
	strcpy( op_table[  98].operator , "SBC" ); strcpy( op_table[  98].add_code , "IMM" ); op_table[  98].opcode = 0xE9; op_table[  98].num_bytes = 2; op_table[  98].base_cycles = 2;
	strcpy( op_table[  99].operator , "SBC" ); strcpy( op_table[  99].add_code , "ABS" ); op_table[  99].opcode = 0xED; op_table[  99].num_bytes = 3; op_table[  99].base_cycles = 4;
	strcpy( op_table[ 100].operator , "SBC" ); strcpy( op_table[ 100].add_code , "ABX" ); op_table[ 100].opcode = 0xFD; op_table[ 100].num_bytes = 3; op_table[ 100].base_cycles = 4;
	strcpy( op_table[ 101].operator , "SBC" ); strcpy( op_table[ 101].add_code , "ABY" ); op_table[ 101].opcode = 0xF9; op_table[ 101].num_bytes = 3; op_table[ 101].base_cycles = 4;
	strcpy( op_table[ 102].operator , "SBC" ); strcpy( op_table[ 102].add_code , "IXR" ); op_table[ 102].opcode = 0xE1; op_table[ 102].num_bytes = 2; op_table[ 102].base_cycles = 6;
	strcpy( op_table[ 103].operator , "SBC" ); strcpy( op_table[ 103].add_code , "IRX" ); op_table[ 103].opcode = 0xF1; op_table[ 103].num_bytes = 2; op_table[ 103].base_cycles = 5;
	strcpy( op_table[ 104].operator , "SEC" ); strcpy( op_table[ 104].add_code , "IMP" ); op_table[ 104].opcode = 0x38; op_table[ 104].num_bytes = 1; op_table[ 104].base_cycles = 2;
	strcpy( op_table[ 105].operator , "SED" ); strcpy( op_table[ 105].add_code , "IMP" ); op_table[ 105].opcode = 0xF8; op_table[ 105].num_bytes = 1; op_table[ 105].base_cycles = 2;
	strcpy( op_table[ 106].operator , "SEI" ); strcpy( op_table[ 106].add_code , "IMP" ); op_table[ 106].opcode = 0x78; op_table[ 106].num_bytes = 1; op_table[ 106].base_cycles = 2;
	strcpy( op_table[ 107].operator , "STA" ); strcpy( op_table[ 107].add_code , "ABS" ); op_table[ 107].opcode = 0x8D; op_table[ 107].num_bytes = 3; op_table[ 107].base_cycles = 4;
	strcpy( op_table[ 108].operator , "STA" ); strcpy( op_table[ 108].add_code , "ABX" ); op_table[ 108].opcode = 0x9D; op_table[ 108].num_bytes = 3; op_table[ 108].base_cycles = 5;
	strcpy( op_table[ 109].operator , "STA" ); strcpy( op_table[ 109].add_code , "ABY" ); op_table[ 109].opcode = 0x99; op_table[ 109].num_bytes = 3; op_table[ 109].base_cycles = 5;
	strcpy( op_table[ 110].operator , "STA" ); strcpy( op_table[ 110].add_code , "IXR" ); op_table[ 110].opcode = 0x81; op_table[ 110].num_bytes = 2; op_table[ 110].base_cycles = 6;
	strcpy( op_table[ 111].operator , "STA" ); strcpy( op_table[ 111].add_code , "IRX" ); op_table[ 111].opcode = 0x91; op_table[ 111].num_bytes = 2; op_table[ 111].base_cycles = 6;
	strcpy( op_table[ 112].operator , "STX" ); strcpy( op_table[ 112].add_code , "ABS" ); op_table[ 112].opcode = 0x8E; op_table[ 112].num_bytes = 3; op_table[ 112].base_cycles = 4;
	strcpy( op_table[ 113].operator , "STY" ); strcpy( op_table[ 113].add_code , "ABS" ); op_table[ 113].opcode = 0x8C; op_table[ 113].num_bytes = 3; op_table[ 113].base_cycles = 4;
	strcpy( op_table[ 114].operator , "TAX" ); strcpy( op_table[ 114].add_code , "IMP" ); op_table[ 114].opcode = 0xAA; op_table[ 114].num_bytes = 1; op_table[ 114].base_cycles = 2;
	strcpy( op_table[ 115].operator , "TAY" ); strcpy( op_table[ 115].add_code , "IMP" ); op_table[ 115].opcode = 0xA8; op_table[ 115].num_bytes = 1; op_table[ 115].base_cycles = 2;
	strcpy( op_table[ 116].operator , "TSX" ); strcpy( op_table[ 116].add_code , "IMP" ); op_table[ 116].opcode = 0xBA; op_table[ 116].num_bytes = 1; op_table[ 116].base_cycles = 2;
	strcpy( op_table[ 117].operator , "TXA" ); strcpy( op_table[ 117].add_code , "IMP" ); op_table[ 117].opcode = 0x8A; op_table[ 117].num_bytes = 1; op_table[ 117].base_cycles = 2;
	strcpy( op_table[ 118].operator , "TXS" ); strcpy( op_table[ 118].add_code , "IMP" ); op_table[ 118].opcode = 0x9A; op_table[ 118].num_bytes = 1; op_table[ 118].base_cycles = 2;
	strcpy( op_table[ 119].operator , "TYA" ); strcpy( op_table[ 119].add_code , "IMP" ); op_table[ 119].opcode = 0x98; op_table[ 119].num_bytes = 1; op_table[ 119].base_cycles = 2;
	strcpy( op_table[ 120].operator , "XXX" ); strcpy( op_table[ 120].add_code , "XXX" ); op_table[ 120].opcode = 0x00; op_table[ 120].num_bytes = 0; op_table[ 120].base_cycles = 0;
	strcpy(op_table[121].operator, "XXX");
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

	}//}}}

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



