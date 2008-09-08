;Program Name: hello.asm.
;
;Version: 1.02.
;
;Description: Print all characters in Mess using OutChar.

;**************************************
;      Program entry point.
;**************************************
	org 0200h

Main *

;Point X to first character of Mess.
	ldx #0d
    
LoopTop *
;Grab a character from Mess.
	lda Mess,x
	
;If the character is the 0 which is at the end
; of Mess, then exit.
	cmp #0d
	beq DonePrint
	
;Call the OutChar monitor utility subroutine.
	jsr E003h
	
;Point X to the next character in Mess and loop back.
	inx
	jmp LoopTop
	
DonePrint *
	
;Exit the program.
	brk

;**************************************
;        Variables area.
;**************************************
Mess	dbt "Hello"
	dbt 0d

	end

