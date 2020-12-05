#if defined WIN32

#ifdef QRCODE2DGENERATOR_EXPORTS
#define QRCODE2DGENERATOR_EXPORT __declspec(dllexport)
#else
#define QRCODE2DGENERATOR_EXPORT __declspec(dllimport)
#endif

#elif __linux__

#define QRCODE2DGENERATOR_EXPORT
#else

#define QRCODE2DGENERATOR_EXPORT

#endif
