;Program Name: hello3.asm.
;
;Version: 1.1.
;
;Description: Print all characters in Mess using PrntMess and
; equs.
;
;Assumptions: When added, the numbers will not be 
; greater than 255.

     ;************************************************************
     ;              Monitor Utility Subroutine Jump Table.
     ;************************************************************
OutChar   equ 1003h ;Output byte in A register to serial port.
                     
GetChar   equ 1006h ;Get a byte from the serial port.
                     
GetCharW  equ 1009h ;Wait and get a byte from the serial port.
                     
PrntMess  equ 100Ch ;Print a message to the serial port.
                     
OutSpace  equ 100Fh ;Output spaces to the serial port.                                 
                     
OutHex    equ 1012h ;Output a HEX number to the serial port.
                     
DgtToBin  equ 1015h ;Convert an ASCII digit into binary.
                     
GetLine   equ 1018h ;Input a line from the serial port.
          


;**************************************                  
;      Program entry point.
;**************************************
	org 0200h                                                       

Main *
	ldx #mess<
	ldy #mess>
	jsr PrntMess
	
;Exit the program.
	brk

;**************************************
;        Variables area.
;**************************************
mess	dbt "Hello3"
	    dbt 0d

	end

