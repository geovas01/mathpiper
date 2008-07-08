;Program Name: largest.asm.
;
;Version: 1.0.
;
;Description: Find the largest number in a list 
; of 100 numbers.
;
;Assumptions: The numbers in the list will be no 
; lower than 0 and no higher than 255.


;**************************************
;Program entry point.
	org 0200h

;This program will use the X register to point
; to the current number in the list
; instead of a variable called 'index'.
;
;Initialize register X to refer to offset 0 into
; the list of numbers starting at 'list'.
	ldx #0d
	
;Compare the number that X is referring to in the
; list to the number that is in 'largest'.  If the
; number being pointed to is larger than the contents
; of the variable 'largest', then fall through to
; the code that will replace the contents of 'largest'
; with the new number.
LoopTop  *
	lda list,x
	cmp largest
	bcc NotLarger
	
IsLarger  *

;Replace the number in 'largest' with the current
; number from the list.
	sta largest

NotLarger  *

;Increment the X register so that it references the
; next number in the list.
	inx

;Check to see if the end of the list has been reached.
; If it has, then exit the program.  If it has not, 
; then branch back to the top of the loop.
CheckListEnd  *
	cpx #100d
	bne LoopTop
	
;Exit the program.
	brk


;**************************************
;Variables area.
;
;In this program, the variables area is being placed
; below the code because the variables take up
; a significant amount of memory.  This makes
; it more difficult to determine where the variables
; area ends so that the start of the code area
; can be determined.


;Holds the largest number 
; encountered so far in the list.
; ( Notice that variables can
; be initialized here instead of
; in the code as an option ).
largest dbt 0d


;This list contains 100 numbers ranging from 0
; to 255.
list	dbt 59d,61d,37d,128d,71d,150d,195d,130d,69d,84d
	dbt 171d,227d,99d,214d,233d,136d,80d,253d,242d
	dbt 112d,221d,151d,101d,117d,76d,226d,174d,205d
	dbt 84d,78d,139d,89d,195d,243d,69d,128d,217d,215d
	dbt 57d,100d,227d,226d,233d,238d,229d,228d,135d
	dbt 140d,98d,211d,245d,120d,206d,198d,47d,191d
	dbt 239d,27d,236d,12d,242d,148d,98d,11d,38d,189d
	dbt 238d,225d,142d,214d,214d,21d,75d,17d,190d
	dbt 178d,123d,125d,123d,10d,166d,123d,135d,220d
	dbt 193d,46d,248d,222d,63d,206d,197d,101d,144d
	dbt 201d,233d,12d,241d,85d,180d,29d
	
	end
