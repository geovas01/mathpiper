;Program Name: hello2.asm.
;
;Version: 1.1.
;
;Description: Print all of the characters in Mess using
; PrntMess

;**************************************
;      Program entry point.
;**************************************
	org 0200h

Main *
;Load the low byte of address of Mess into X.
	ldx #Mess<
	
;Load the high byte of address of Mess into Y.
	ldy #Mess>
	
;Call PrntMess monitor utility subroutine.
	jsr 100ch
	
;Exit the program.
	brk

;**************************************
;        Variables area.
;**************************************
Mess	dbt "Hello2"
	dbt 0d

	end

