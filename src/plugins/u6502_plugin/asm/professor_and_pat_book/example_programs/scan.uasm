;Program Name: scan.asm.
;
;Version: 1.0.
;
;Description: Scan list1 and determine how many capital A's and lower case
; a's it contains.
;
;Assumptions: The numbers in the list will be no 
; lower than 0 and no higher than 255.


;**************************************
;Program entry point.
	org 0200h

;The X register will be used to point to each ASCII character in string1
	ldx #0d
	

LoopTop  *
	lda string1,x
	
	
;If we have reached the 0 that has been placed at the end of the string, then
; exit the program.
	cmp #0d
	beq EndOfList
	
CheckLowerCase  *
;If the current character is a lower case 'a', then increment LowerCaseCount.
	cmp #'a'
	bne CheckUpperCase
	inc LowerCaseCount
	jmp NextCharacter
	
CheckUpperCase  *
;If the current character is an upper case 'a', then increment UpperCaseCount.
	cmp #'A'
	bne NextCharacter
	inc UpperCaseCount
	
NextCharacter  *
	inx
	jmp LoopTop
	
EndOfList  *
	
;Exit the program.
	brk


;**************************************
;Variables area.

LowerCaseCount dbt 0d
UpperCaseCount dbt 0d

;This list contains a string of ASCII characters.
string1	dbt "A bird in the hand is worth two in the bush.  Early to bed and "
	dbt "early to rise makes a person healthy, wealthy, and wise", dbt 0d

	end

