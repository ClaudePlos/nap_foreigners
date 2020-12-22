import * as pdfMake from './dist/pdfmake.js';
import * as pdfFonts from './dist/vfs_fonts.js';
pdfMake.vfs = pdfFonts.pdfMake.vfs;

function generateNotification(container, prcName, prcSurname, prcNumber, dateNow, frmName, address, workerBirthDate) {
    console.log(prcName);
    var docDefinition = {
        content: [
            {
                alignment: 'right',
                text: 'Łódź, dnia ' + dateNow,
                fontSize: 12,
                bold: true
            },
            {
                alignment: 'left',
                text: 'Podmiot powierzający wykonanie pracy cudzoziemcowi',
                fontSize: 12,
                margin: [0, 40, 0, 0]

            },
            {
                //TODO
                alignment: 'left',
                text: 'NAZWA FIRMY/IMIĘ I NAZWISKO ' + frmName,
                fontSize: 12,
                bold: true,
                margin: [0, 25]

            },
            {
                //TODO
                alignment: 'left',
                text: 'ADRES SIEDZIBY/ZAMIESZKANIA ' + address,
                fontSize: 12,
                bold: true,
                margin: [0, 25]

            },
            {
                alignment: 'center',
                text: 'POWIADOMIENIE PODMIOTU POWIERZAJĄCEGO WYKONANIE PRACY\n' +
                    'CUDZOZIEMCOWI DOTYCZĄCE OKOLICZNOŚCI, O KTÓRYCH MOWA W ART. 882\n' +
                    'UST.13 I 15 USTAWY Z DNIA 20 KWIETNIA 2004 R. O PROMOCJI ZATRUDNIENIA\n' +
                    'I INSTYTUCJACH RYNKU PRACY',
                fontSize: 14,
                style: 'header',
                bold: true,
                margin: [0,30]
            },
            {
                alignment: 'left',
                text: 'Informuję, iż cudzoziemiec ......' + prcName + ' ' + prcSurname + '......',
                fontSize: 12,
                margin: [0,10]
            },
            {
                text: '(imię i nazwisko)',
                fontSize: 8,
                margin: [190,0]
            },
            {
                alignment: 'left',
                //TODO zamianst 00/00/0000 wpisać prawidłową datę
                text:'urodzony/a dnia ' + workerBirthDate + ', którego oświadczenie o powierzeniu',
                fontSize: 12,
                margin: [0,10]

            },
            {
                text: '(data urodzenia)',
                fontSize: 8,
                margin: [110,0]
            },
            {
                alignment: 'left',
                text:'wykonywania pracy cudzoziemcowi zostało wpisane do ewidencji oświadczeń\n' +
                    'pod numerem ......'+prcNumber+ '...... :',
                fontSize: 12,
                margin: [0,10]
            },
            {
                text: '(numer oświadczenia)',
                fontSize: 8,
                margin: [80,0]
            },
            {
                alignment: 'left',
                text: 'rozpoczął pracę z dniem ( data rozpoczęcia pracy )',
                fontSize: 12,
                margin: [25,10]
            },
            {
                alignment: 'left',
                text: 'nie podjął pracy w terminie 7 dni od dnia rozpoczęcia pracy określonego w ewidencji',
                fontSize: 12,
                margin: [25,10]
            },
            {
                alignment: 'left',
                text: 'zakończył pracę z dniem ( data zakończenia pracy +data+ )',
                fontSize: 12,
                margin: [25,10]
            },
            {
                alignment: 'left',
                text: 'Właściwe zaznaczyć',
                fontSize: 8,
                margin: [10, 0]
            },
            {
                columns: [
                    {
                        alignment: 'center',
                        text: '......ŁÓDŹ, '+ dateNow + '......',
                        margin: [0, 40, 0, 0]
                    },
                    {
                        alignment: 'center',
                        text: '................................',
                        margin: [0, 40, 0, 0]
                    }
                ]

            },
            {
                columns: [
                    {
                        alignment: 'center',
                        text: 'miejscowość i data',
                        fontSize: 8,
                        margin: [25,0]
                    },
                    {
                        alignment: 'center',
                        text: 'czytelny podpis podmiotu powierzającego wykonanie\n' +
                            'pracy cudzoziemcowi/osoby upoważnionej\n' +
                            'do działania w imieniu podmiotu',
                        fontSize: 8,
                        margin: [0,0]

                    }
                ]
            },
        ]
    }
    pdfMake.createPdf(docDefinition).download('file.pdf', function () {
        alert('your pdf is done');
    });

}

window.generateNotification = generateNotification;