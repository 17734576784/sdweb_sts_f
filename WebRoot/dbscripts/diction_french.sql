delete from diction 
where type_no in(16,18,1548,40,43,1520,53,
163,1547,27,72,1530,1504,1540,1501,17,23)
INSERT INTO [diction] VALUES (16, '安装地点', 'Sous-sol', 4, '');
GO
INSERT INTO [diction] VALUES (16, '安装地点', 'Sous-station de transformateur de boîte', 1, '');
GO
INSERT INTO [diction] VALUES (16, '安装地点', 'Boîtier de mesure intérieur', 0, '');
GO
INSERT INTO [diction] VALUES (16, '安装地点', 'Autres', 6, '');
GO
INSERT INTO [diction] VALUES (16, '安装地点', 'Boîtier de mesure extérieur', 2, '');
GO
 
INSERT INTO [diction] VALUES (18, '变压器类型', 'Transformateur de mélange', 1, '');
GO
INSERT INTO [diction] VALUES (18, '变压器类型', 'Transformateur public', 2, '');
GO
INSERT INTO [diction] VALUES (18, '变压器类型', 'Transformateur spécial', 0, '');
GO 
 
GO
INSERT INTO [diction] VALUES (1548, '档案权限', 'Modifier', 2, '');
GO
INSERT INTO [diction] VALUES (1548, '档案权限', 'Aucun', 0, '');
GO
INSERT INTO [diction] VALUES (1548, '档案权限', 'Requete', 1, '');
GO
 
GO
INSERT INTO [diction] VALUES (40, '电表生产厂家', 'ABB', 6, '');
GO
INSERT INTO [diction] VALUES (40, '电表生产厂家', 'KELIN', 0, '');
GO
INSERT INTO [diction] VALUES (40, '电表生产厂家', 'SAMSUNG', 4, '');
GO
INSERT INTO [diction] VALUES (40, '电表生产厂家', 'WASION', 7, '');
GO
 
INSERT INTO [diction] VALUES (43, '费控类型', 'Contrôle des coûts de la station maître', 1, '');
GO
INSERT INTO [diction] VALUES (43, '费控类型', 'Contrôle des coûts terminaux', 0, '');
GO

INSERT INTO [diction] VALUES (1520, '费率类型', 'Tarif unique', 3, '');
GO
INSERT INTO [diction] VALUES (1520, '费率类型', 'Taux de pas', 2, '');
GO
INSERT INTO [diction] VALUES (1520, '费率类型', 'Taux Tou', 1, '');
GO

INSERT INTO [diction] VALUES (53, '计费方式', 'Consommation dénergie', 2, '');
GO
INSERT INTO [diction] VALUES (53, '计费方式', 'Montant dargent', 1, '');
GO
INSERT INTO [diction] VALUES (163, '缴费方式', 'Jeton', 0, '');
GO
INSERT INTO [diction] VALUES (1547, '阶梯电价电表费率类型', 'Tarif Poly', 1, '');
GO
INSERT INTO [diction] VALUES (1547, '阶梯电价电表费率类型', 'Tarif unique', 0, '');
GO
INSERT INTO [diction] VALUES (1547, '阶梯电价电表费率类型', 'Tarif par étapes', 3, '');
GO
INSERT INTO [diction] VALUES (27, '是否标志', 'Non', 0, '');
GO
INSERT INTO [diction] VALUES (27, '是否标志', 'Oui', 1, '');
GO
 
INSERT INTO [diction] VALUES (72, '无线网络在线方式', 'Temps réel en ligne (LIP nest pas fixe/Battement de coeur)', 2, '');
GO
INSERT INTO [diction] VALUES (72, '无线网络在线方式', 'Temps réel en ligne (IP fixe/Battement de coeur)', 0, '');
GO
INSERT INTO [diction] VALUES (72, '无线网络在线方式', 'Temps réel en ligne (IP fixe)', 1, '');
GO
 
INSERT INTO [diction] VALUES (1530, '用户状态', 'État dannulation du compte', 50, '');
GO
INSERT INTO [diction] VALUES (1530, '用户状态', 'État normal', 1, '');
GO
INSERT INTO [diction] VALUES (1530, '用户状态', 'Létat original', 0, '');
GO
INSERT INTO [diction] VALUES (1530, '用户状态', 'Etat de pause', 49, '');
GO
 
INSERT INTO [diction] VALUES (1504, '预付费表类型', 'Compteur à prépaiement monophasé KE', 1, '');
GO
INSERT INTO [diction] VALUES (1504, '预付费表类型', 'Compteur prépayé triphasé KW', 3, '');
GO
 
INSERT INTO [diction] VALUES (1540, '预付费操作类型', 'Annulation de compte', 50, '');
GO
INSERT INTO [diction] VALUES (1540, '预付费操作类型', 'Ouvrir un compte', 1, '');
GO
INSERT INTO [diction] VALUES (1540, '预付费操作类型', 'Létat original', 0, '');
GO
INSERT INTO [diction] VALUES (1540, '预付费操作类型', 'Achat délectricité', 2, '');
GO

INSERT INTO [diction] VALUES (1501, '预付费权限范围', 'Tous les terminaux', 0, '');
GO
INSERT INTO [diction] VALUES (1501, '预付费权限范围', 'Terminaux de zone', 1, '');
GO
INSERT INTO [diction] VALUES (1501, '预付费权限范围', 'Terminals of Manager', 2, '');
GO

INSERT INTO [diction] VALUES (17, '运行状态', 'Mis en service', 1, '');
GO
INSERT INTO [diction] VALUES (17, '运行状态', 'Arrêtez', 50, '');
GO
INSERT INTO [diction] VALUES (17, '运行状态', 'Opération en attente', 4, '');
GO
 
INSERT INTO [diction] VALUES (23, '终端类型', 'Concentrateur', 3, '');
GO
INSERT INTO [diction] VALUES (23, '终端类型', 'Terminal de transformateur distribué ', 2, '');
GO
INSERT INTO [diction] VALUES (23, '终端类型', 'Terminal dénergie électrique', 1, '');
GO
INSERT INTO [diction] VALUES (23, '终端类型', 'Terminal de contrôle de charge', 0, '');
GO

