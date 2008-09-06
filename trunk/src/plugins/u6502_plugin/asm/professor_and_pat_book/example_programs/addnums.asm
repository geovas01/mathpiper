;Program Name: addnums.asm.
;
;Version: 1.1.
;
;Description: Use a subroutine to add 2 numbers
; All communications between the main routine and 
; the subroutine are handeled with registers.
;
;Assumptions: When added, the numbers will not be 
; greater than 255.


;**************************************
;      Program entry point.
;**************************************
	org 0200h

Main *
	ldx #1d
	ldy #2d
	jsr AddNums
	sta answer
	
;Exit the program.
	brk
	
	
;**************************************
;        Subroutines area.
;**************************************

;**************************************
;AddNums subroutine.
;
;Information passed in:
;X and Y hold the two numbers to be added.
;
;Information returned:
;The result is returned in the 'A' register.
;**************************************
AddNums *
	txa
	sty temp
	clc
	adc temp
	rts
	

;**************************************
;        Variables area.
;**************************************
temp	dbt 0d
answer	dbt 0d

	end

