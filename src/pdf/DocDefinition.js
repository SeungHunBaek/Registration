"use strict";

function createParticipants(inputData) {
  var participants = [];

  participants.push({
    style: 'table_noMargin',
    table: {
      widths: [95, 120, 40,60, 70,90],
      body: [
        [
          { text: 'Participant Name'},{text: inputData.list[0].name || '' },
          { text: 'Gender'},{text:  inputData.list[0].gender || '' },
          { text: 'Date of Birth'},{text: inputData.list[0].birthDay || '' }
        ]
      ]
    }
  });

  if (inputData.list.length == 2 && inputData.list[1].name !== '') {
    participants.push({
      style: 'table_noMargin',
      table: {
        widths: [95, 120, 40,60, 70,90],
        body: [
          [
            { text: 'Participant Name'},{text: inputData.list[1].name || '' },
            { text: 'Gender'},{text:  inputData.list[1].gender || '' },
            { text: 'Date of Birth'},{text: inputData.list[1].birthDay || '' }
          ]
        ]
      }
    });
  } else {
    participants.push({ text: '' });
  }

  return participants;
}

class DocDefinition {
  constructor(inputData) {
    this.inputData = inputData;
  }

  get() {
    var inputData = this.inputData;
    var participants = createParticipants(inputData);
    var logoPath = __dirname + '/../../images/Round1-Logo.PNG';

    var docDefinition = {
    		pageSize: 'LETTER',

      footer: function(currentPage, pageCount) {
        return {
          columns: [
            {
              alignment: 'center',
              text: [ currentPage.toString(), ' of ', pageCount.toString() ]
            }
          ]
        }
      },

      content: [
        { //title
          stack: [
            {
              text: 'TEXAS (Sticker)\n ROUND ONE ENTERTAINMENT, INC.\n\n KIDS RECREATION AREA RULES AND RELEASE AGREEMENT \n (REQUIRED TO ENTER KIDS RECREATION AREA) '
			, alignment: 'center'
			, bold: true,
            }
          ],

        },
        {
          text: 'IN CONSIDERATION FOR BEING ALLOWED TO ENTER AND USE THE KIDS RECREATION AREA AT ROUND ONE ENTERTAINMENT, INC. (\“ROUND ONE\”), THE UNDERSIGNED PARENT/GUARDIAN, FOR HIS OR HER BEHALF, AND ON BEHALF OF THE PARENT/GUARDIAN\’S PARTICIPANT(S) CHILD/CHILDREN (\“PARTICIPANT\”) IDENTIFIED BELOW, ACKNOWLEDGES, UNDERSTANDS, AND AGREES TO THE FOLLOWING: \n\n'
		, style: 'basic_font'
        },
{			columns: [
				{
		         text: 'RULUES \n',
				 alignment: 'center',
		         fontSize: 9,
				 bold: true,


				},
				{
				text: ' \n'
				,alignment: 'center'
				, bold: true,
				},

			]
        },

        { //content
        	columns: [
        		{
		  	    	style: 'consent_font',
		  		    text: [
			          '1. Participant(s) in the Kids Recreation Area MUST be 12 years old or younger and accompanied by a Parent or Guardian at all times.  Parents/Guardians are responsible for supervising their children and for the conduct of their children.  Round One will not be responsible for any injuries, losses or damage allegedly sustained in the Kids Recreation Area.'
		            +'\n2. All persons between the ages of 13 and 17 years are PROHIBITED from entering the Kids Recreation Area for any reason.'
		            +'\n3. All Parents/Guardians must be at least 18 years old.'
		            +'\n4. Parents/Guardians are PROHIBITED from leaving the Kids Recreation Area without the Participant(s).'
		            +'\n5. A maximum of two (2) Participant children are allowed per Parent/Guardian.'
		            +'\n6. Any medical condition that may hinder the Participant child’s enjoyment of the Kids Recreation Area must be reported to our staff.'
		            +'\n7. All Participants and Parents/Guardians must wash their hands or use hand sanitizer before entering the Kids Recreation Area. Round One uses alcohol and cleaning solutions and supplies to periodically clean the Kids Recreational Area.  Round One will not be responsible for any allergic reactions which may occur due to cleaning of the Kids Recreational Area.'
		            +'\n8. No shoes are allowed inside the Kids Recreation Area. Socks are required to be worn by everyone in the Kids Recreation Area, adults included. Bare feet are not allowed'
		            +'\n9. Sharp objects, including but not limited to, knives, needles, pins, and cutters, are not allowed inside the Kids Recreation Area.'
		            +'\n10. Certain activities within the Kids Recreation Area are restricted to toddlers or small children only, and Parents/Guardians and Participants agree to abide by such restrictions.'
		            +'\n11. Children must be fully clothed in clean, dry clothes when playing in the Kids Recreation Area.  Children who are being potty-trained must wear a diaper. '
		            +'\n12. Children who are “newly” potty-trained must have an extra set of clean clothes readily available in the event of an accident.  Round One staff must be immediately notified of any such urine or fecal spills in the kids Recreation Area'
		            +'\n13. All food and drinks are prohibited in the Kids Recreation Area.'
		            +'\n14. Chewing gum is prohibited inside the Kids Recreation Area.'
		            +'\n15. All jewelry and loose articles must be removed before entering the Kids Recreation Area.'
		            +'\n16. Round One reserves the right to stop any and all activities in the Kids Recreation Area and to remove any Participants and guests at Round One’s sole and absolute discretion without a refund'
		            +'\n17. Participants and guests who fail to comply with these rules may be denied admission or removed from the Kids Recreation Area and/or Round One’s premises without a refund.'
		            +'\n18. Round One is not responsible for any damages or loss related to damaged or lost clothing, jewelry and/or other personal belongings.'
		            +'\n19. All Participants and guests will be held liable for any damage to Round One’s equipment and/or facilities.'
		            +'\n20. Participants and guests who are feeling sick must comply with Round One’s requests to leave the Kids Recreation Area and/or Round One’s premises.'
		            +'\n21. Mall Security and/or the Police may be called in the event of any dispute between customers, Parents/Guardians or participant children in the Kids Recreational Area.  Round One will not be responsible to resolve any disputes.'
		            +'\n22. All Participants and Parents/Guardians agree to keep the stickers on at all times in the Kids Recreation Area and agree not to remove the stickers while in the Kids Recreation Area.'
		            +'\nI represent that I am the Parent or legal Guardian of the Participant(s) named below or I have obtained permission from the Parent or legal Guardian of the Participant(s) named below to execute this Release and Agreement on their behalf.  I, for myself, the Participant(s), our heirs, beneficiaries, representatives, and next of kin agree as follows:'
		            ,{
			         text: '\n RELEASE ',
					 alignment: 'center',
					 bold: true,
			         fontSize: 9,
					},
					'\n1. I acknowledge and understand that there are known and unknown risks associated with participation in Round One activities and the use of the Kids Recreation Area, and any and all other Round One equipment, which include but are not limited to contusions, fractures, scrapes, cuts, bumps, allergic reactions, paralysis, blindness, scarring, abduction, kidnapping, assault, permanent disability, or death. '
		            +'\n2. I, for myself and the Participant(s) named, willingly assume the risks associated with participation and accept that there are also risks that may arise due to other Participants, which I also willingly assume and agree to indemnify Round One for any and all claims, damages, attorneys’ fees and costs. '
		            +'\n3. I, for myself and the Participant(s) named, agree that the Participant(s) named and I shall comply with all stated and customary terms, posted safety signs, rules, and verbal instructions as conditions for participation in or entry to Round One’s Kids Recreation Area. '
	            	+'\n4. I acknowledge that Round One does not have a checkout procedure or any confirmation that the Participant(s) are matched with the Parent/Guardian herein unless I stay with the Participant(s) at all times. '
	            	+'\n5. I, for myself and the Participant(s) named, agree that Round One’s sticker system for pairing Parents/Guardians and Participants does not absolve the Parents/Guardians responsibility to supervise the Participant child/children at all times and Round One will not be liable for any injury, accident, blindness, scarring, disability, abduction, kidnapping, assault, or death which may occur. '
		            ]
  			  	},
  			  	{
  			  		style: 'consent_font',
  			  		text: [
  			  		       '  I, for myself and the Participant(s) named, am aware that the issuance of a sticker is not intended to act as any confirmation that the Participant(s) are matched with the Parent/Guardian and that the issuance of a sticker merely indicates that the Participant(s) and Parents/Guardians have satisfied the requirements to enter the Kids Recreation Area.'
  			  		       +'\n6. I as the Parent/Guardian acknowledge and agree that I as the Parent/Guardian must supervise the Participant child/children at all times, and will not leave the Participant child/child unattended.  If I as the Parent/Guardian leave the Participant child/children unattended, then I acknowledge and agree to be solely liable for any and all injury, accident, assault, abduction, missing child, or death which may occur and to indemnify Round One for any and all claims, damages, attorneys’ fees, and costs. '
  			  		       +'\n7. If I ever leave the Participant child/children unattended, I acknowledge and agree to be solely and absolutely liable for any injury, accident, blindness, scarring, disability, abduction, kidnapping, assault, or death which may occur and agree to indemnify Round One from all claims, damages, attorneys’ fees and costs. '
  			  		       +'\n8. I, for myself and the Participant(s), acknowledge and understand that I am solely responsible for any injury, accident, blindness, scarring, disability, abduction, kidnapping, assault, or death which may occur to the Participant(s) or myself in the Round One Kids Recreation Area.  I, for myself and the Participant(s) named, am aware that Round One employees are not babysitters and agree that Round One is not responsible to watch or supervise the children and have no duty or any legal responsibility to prevent injuries, blindness, scarring, disability, abduction, kidnapping, assault, or death. '
  			  		       +'\n9. I, for myself, the Participant(s), our heirs, beneficiaries, assigns, representatives, and next of kin, agree to hold harmless, release, waive, discharge, and covenant not to sue Round One, its directors, officers, shareholders, related entities, attorneys, security personnel, agents, and employees (the “Releasees”) from all liability for any and all injuries, abduction, kidnapping, assault,  losses, or damages, and any claims or demands for any injury or death to any Participant, whether caused by the negligence of the Releasees or otherwise. '
  			  		       +'\n10. I, for myself, the Participant(s), our heirs, beneficiaries, assigns, representatives, and next of kin, agree to indemnify and hold harmless the Releasees and each of them from any loss, and all injuries, disabilities, abductions, kidnappings, assaults, loss, liability, damage, attorneys’ fees or cost which may be incurred arising out of or related to participation in or entry to Round One’s Kids Recreation Area, whether caused by the negligence of the Releasees or otherwise. '
  			  		       +'\n11. I, for myself, the Participant(s), our heirs, beneficiaries, assigns, representatives, and next of kin, expressly waive any and all provisions, rights, and benefits conferred by any law of any state or territory of the U.S.A., or principle of common law or otherwise,  with respect to claims which we do not know or suspect to exist in our favor at the time of executing this Release, which if known by us, must have materially affected our decision to execute this Release to the maximum extent permissible. '
  			  		       +'\n12. I have explained Round One’s Rules and the risks associated with the Kids Recreation Area and Round One’s equipment and facilities to the Participants below, and hereby represent that the Participants named below fully understand(s) Round One’s Rules, the risks associated with the Kids Recreation Area and Round One’s equipment and facilities, and agree(s) to abide by Round One’s rules. '
  			  		       +'\n13. I, for myself, the Participant(s), our heirs, beneficiaries, assigns, representatives, and next of kin, agree that this Release is intended to be as broad and inclusive as is permitted by state and federal law, and that if any portion thereof is held invalid, it is agreed that the balance of this Release shall, notwithstanding, continue in full legal force and effect. '
  			  		       +'\n\nI, FOR MYSELF AND THE PARTICIPANT(S) NAMED, REPRESENT THAT I AM THE PARENT OR LEGAL GUARDIAN OF THE PARICIPANT(S) AND AM AUTHORIZED TO SIGN THIS AGREEMENT.  I ACKNOWLEDGE THAT I HAVE READ COMPLETELY AND FULLY UNDERSTAND ALL ASPECTS OF THIS AGREEMENT AND I AGREE TO ALL OF THE PROVISIONS IN THIS AGREEMENT IN THEIR ENTIRETY.'
  			  		       +'\nMEDICAL AUTHORIZATION AND CONSENT '
  			  		       +'\n\nI represent that I am the Parent or Legal Guardian of the Participant(s) listed herein.  I hereby authorize and consent for Round One employees or agents to administer general first aid treatment for any minor injuries or illnesses experienced by the Participant(s) or to. If the injury or illness is life threatening or in need of emergency treatment, I authorize Round One to summon any and all professional emergency personnel to attend, transport, and treat the minor and to issue consent for any X-ray, anesthetic, blood transfusion, medication, or other medical diagnosis, treatment, or hospital care deemed advisable by, and to be rendered under the general supervision of, any licensed physician, surgeon, dentist, hospital, or other medical professional or institution duly licensed to practice in the state in which such treatment is to occur.  I agree to assume financial responsibility for all expenses of such care. '
  			  		       +'\nIt is understood that this authorization is given in advance of any such medical treatment, but is given to provide authority and power on the part of Round One’s employees or agents in the exercise of his or her best judgment upon the advice of any such medical or emergency personnel.'
  			  		       ]
  			  	},
		  	]
	  	},
        	{
	  			pageBreak: 'before',
        		columns: [
        			{
        			style: 'consent_font',
        				text:[

        					'\nAny controversy or claim arising out of or relating to this Release, shall be settled by arbitration administered by the American Arbitration Association (“AAA”) in accordance with AAA Rules in Dallas County, Texas and shall be governed by the laws of the State of Texas, and judgment on the award rendered by the arbitrator(s) may be entered in any court having jurisdiction thereof.  The arbitrator shall have the authority to order such discovery, by way of deposition, interrogatory, document production, or otherwise, as the arbitrator considers necessary to a full and fair exploration of the issues in dispute, consistent with the expedited nature of arbitration.  The undersigned agrees to pay for all costs and the arbitrator’s fees.'
        				]
		  			}
	  			]
	  		},


	  		{
  		     text:'Participants',
	         margin : [0,10,0,0],
  		     bold:true
    		},
    		{
	  			text: '- Info',
	  			fontSize: 10,
	  			margin : [10,0,0,5],
	  		},
    		participants[0],
    		participants[1],
    		{
	  			text: 'Parent',
	  			bold: true,
	  			margin : [0,5,0,0],

	  		},
	  		{
	  			text: '- Are you over the age of 18? YES',
	  			fontSize: 10,
	  			margin : [10,0,0,0],
	  		},
	  		{
	  			text: '- Info',
	  			fontSize: 10,
	  			margin : [10,0,0,0],
	  		},

    		{
	  			style: 'tableMargin',
	  			table: {
	        	widths: [90, 180, 70,155],
	  				body: [
	  					['Parent / Legal Guardian Name', inputData.parentName,'Relationship with Listed', inputData.relationship],
	  					['Address',inputData.address,'City',inputData.city],
	  					['State',inputData.state,'Zipcode',inputData.zipCode],
  						[{ colSpan: 1,text: 'Contact Phone \nNumber '},{ colSpan: 1,text: inputData.phone},'Date', inputData.today]
	  				]
	  			}
	  		},

	  		{
	  			text: '- Signature',
	  			fontSize: 10,
	  			margin : [10,0,0,0],
	  		},
	  		{
	  			style: 'tableMargin',
	  			table: {
					body: [
						[{
    			      border: [true, true, true, true],
    				  image: inputData.signImgData,
    		    	  width: 293,
    		  		  height: 88,
    		      	alignment: 'center',
      		      margin : [2,2,2,2]
  		       }],
  				 ]
  				}
		  	},
		  	{
	  			text: '- License',
	  			fontSize: 10,
	  			margin : [10,0,0,0],
	  		},
	  		{
        		style: 'tableMargin',
	  			table: {
					body: [
						[{
    			    border: [true, true, true, true],
    				  image: inputData.cameraImgData,
    		    	width: 293,
    		  		height: 220,
    		      alignment: 'left',
      		      margin : [2,2,2,2]
		        }],
					]
				}

	  		},
      ],

      styles: {
	        header: {
	          fontSize: 12,
	          bold: true,
	          alignment: 'center',
	          margin: [0, 30, 0, 20]
	        },

			basic_font: {
	          fontSize: 7,
	        },
	        consent_font: {
	        	fontSize: 6.5,
	        },
			basic_font_margin: {
	          fontSize: 10,
	          bold: true,
	          margin: [5, 5, 5, 5]
	        },
	   	   table_noMargin: {
	          margin: [10, 0, 10, 1],
	          fontSize: 8
	        },

	        small_font: {
	          fontSize: 6.5,
	        },


	        subheader: {
	          fontSize: 10,
	          alignment: 'right',
	          margin: [0, 10, 10, 0]
	        },

	        superMargin: {
	          margin: [10, 0, 15, 10],
	          fontSize: 10
	        },

	        list_form: {
	          fontSize: 13,
	          bold: true,
	          alignment: 'left',
	          margin: [0, 0, 15, 10]
	        },

	        tableMargin: {
	          margin: [10, 5, 10, 10],
	          fontSize: 8
	        },

	        imgalignment: {
	          width: 10,
	          height: 10
	        }
      },
	  defaultStyle: {
	  columnGap: 7,
	},

    }

    return docDefinition;
  }
}

module.exports = DocDefinition;
