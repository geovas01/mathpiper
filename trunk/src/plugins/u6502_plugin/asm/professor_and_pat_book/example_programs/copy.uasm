;Program Name: copy.asm.
;
;Version: 1.0.
;
;Description: Copy the contents of list1 to list2.
;
;Assumptions: The numbers in the list will be no 
; lower than 0 and no higher than 255.


;**************************************
;Program entry point.
	org 0200h

;The X register will be used to point to each number in both list1 and list2.
	ldx #0d
	

;Copy the 10 bytes from list1 to list2.
LoopTop  *
	lda list1,x
	sta list2,x
	
	inx
	
	cpx #10d
	
	bne LoopTop
	
	
;Exit the program.
	brk


;**************************************
;Variables area.

;This list contains 10 bytes.
list1	dbt 41h,42h,43h,44h,45h,46h,47h,44h,49h,4ah

;This byte is placed here to make it easier to see the contents of list1
;and list2 when they are dumped in the monitor.
	dbt 0d

;Set aside 10 memory locations and initialize each one to 0d. 
list2	dbt 10d(0d)

	end

