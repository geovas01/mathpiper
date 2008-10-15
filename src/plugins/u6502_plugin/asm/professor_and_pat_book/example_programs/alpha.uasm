;Program Name: alpha.asm.
;
;Version: 1.01.
;
;Description: Place capital A's in the first 10 bytes of alphaList, capital B's
; in the second 10 bytes of alphalist and so on until alphaList is filled
; with letters.
;
;Assumptions: The numbers in the list will be no 
; lower than 0 and no higher than 255.


;**************************************
;Program entry point.
	org 0200h

;The X register will be used as a pointer.
	ldx #0d
	
	lda #10d
	sta rowCount
	sta columnCount
	
	
;Initialize the 'A' register to be a capital 'A'.
	lda #65d

LoopTop  *
;Place character in list at offset X.
	sta alphalist,x
	
;Point X to the next character position in the list.
	inx
	
;If we have not placed 10 characters in this row yet, then loop.
	dec columnCount
	bne LoopTop
	
;Reset the column counter.
	ldy #10d
	sty columnCount

;Increase to the next letter in the alphabet.
	clc
	adc #1d
	
;If we have not filled 10 rows yet then loop.
	dec rowCount
	bne LoopTop
	
	
EndOfList  *
	
;Exit the program.
	brk


;**************************************
;Variables area.

rowCount 	dbt 0d
columnCount 	dbt 0d
alphalist 	dbt 100d(?)

	end

