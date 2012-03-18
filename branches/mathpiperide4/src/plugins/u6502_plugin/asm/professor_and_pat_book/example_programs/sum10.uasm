;Program Name: sum10.asm.
;
;Version: 1.1
;
;Description: In this program, the sum of the 
; numbers between 1 and 10 inclusive will be 
; determined.

;**************************************
;Variables area.
	org 0200h
	
;Holds the accumulating sum.
sum	dbt d

;Holds the current number
; that is being added to sum and also
; is used to determine when the summing
; loop should be exited.
count 	dbt 0d


;**************************************
;Program entry point.
	org 0210h
	
;Place 0 into variable sum.
	lda #0d
	sta sum
	
;Place 1 into variable count.
	lda #1d
	sta count
	
;Top of summing loop.
LoopTop *

;Add count to sum and place result back into sum.
	lda sum
	clc
	adc count
	sta sum
	
;Add 1 to count.
	inc count
	
;If count is not equal to 11 yet then branch back
; to LoopTop.
	lda count
	cmp #11d
	bne looptop
	
;Exit program.
	brk
	
	end
