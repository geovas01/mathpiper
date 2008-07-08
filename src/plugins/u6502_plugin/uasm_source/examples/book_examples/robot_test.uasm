SERVOL 	    	equ 4000h
SERVOR 	    	equ 4001h
BUTTONL       	equ 4002h
BUTTONR  		equ 4003h
FIELDL        	equ 4004h
FIELDR       	equ 4005h
DISTL        	equ 4006h
DISTR       	equ 4007h
DELAYS          equ 4008h
DELAYMS         equ 4009h
       
 org 0200h
 	
backAndFwd  *
;Drive Straight Ahead for 2 seconds
	lda #100d			 
	sta SERVOL  
	sta SERVOR  
	lda #2d
	sta DELAYS 
;Drive Backwards for 2 seconds
	lda #9ch		 
	sta SERVOL 
	sta SERVOR 
	lda #2d
	sta DELAYS 
;Drive Straight Ahead  
	lda #9ch			 
	sta SERVOL  
	sta SERVOR  
;Until the Drive Straight Ahead 
waitDark  *
	lda FIELDL
	cmp #100d
	bcs waitDark
 
 ;Drive Backwards for 1 seconds
	lda #9ch			 
	sta SERVOL 
	sta SERVOR 
	lda #1d
	sta DELAYS 
	
;SPIN right for .25 seconds
	lda #9ch			 
	sta SERVOL 
	lda #100d	
	sta SERVOR 
	lda #250d
	sta DELAYMS
;SPIN left for .25 seconds
	lda #100d			 
	sta SERVOL 
	lda #9ch	
	sta SERVOR 
	lda #250d
	sta DELAYMS
;DRIVE IN CIRCLE 5 seconds
	lda #100d			 
	sta SERVOL 
	lda #25d	
	sta SERVOR 
	lda #5d
	sta DELAYS
 
	jmp backAndFwd
 
 
       end
